package com.hengsu.bhyy.core.controller;

import com.google.common.cache.Cache;
import com.google.common.collect.ImmutableMap;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.annotation.IgnoreAuth;
import com.hengsu.bhyy.core.model.DoctorModel;
import com.hengsu.bhyy.core.model.SessionUserModel;
import com.hengsu.bhyy.core.service.DoctorService;
import com.hengsu.bhyy.core.service.SmsService;
import com.hengsu.bhyy.core.vo.DoctorVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import com.wlw.pylon.web.rest.annotation.RestApiController;

import com.hengsu.bhyy.core.service.CustomerService;
import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.vo.CustomerVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class CustomerRestApiController {

    private final Logger logger = LoggerFactory.getLogger(CustomerRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    @Qualifier("sessionCache")
    private Cache<String, SessionUserModel> sessionCache;

    @Autowired
    @Qualifier("validateCodeCache")
    private Cache<String, String> validateCodeCache;

    @Autowired
    private SmsService smsService;

    @Value("${sms.templateCode}")
    private String templateCode;

    @GetMapping(value = "/core/customer/{id}")
    public ResponseEnvelope<CustomerVO> getCustomerById(@PathVariable Long id) {
        CustomerModel customerModel = customerService.findByPrimaryKey(id);
        CustomerVO customerVO = beanMapper.map(customerModel, CustomerVO.class);
        ResponseEnvelope<CustomerVO> responseEnv = new ResponseEnvelope<>(customerVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/customer")
    public ResponseEnvelope<Page<CustomerModel>> listCustomer(CustomerVO customerVO, Pageable pageable) {

        CustomerModel param = beanMapper.map(customerVO, CustomerModel.class);
        List<CustomerModel> customerModelModels = customerService.selectPage(param, pageable);
        long count = customerService.selectCount(param);
        Page<CustomerModel> page = new PageImpl<>(customerModelModels, pageable, count);
        ResponseEnvelope<Page<CustomerModel>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/customer/name")
    public ResponseEnvelope<List<Map<String, Object>>> queryByName(@RequestParam("name") String name) throws Exception {
        name = new String(name.getBytes("iso-8859-1"), "utf-8");
        List<Map<String, Object>> mapList = customerService.queryByName(name);
        ResponseEnvelope<List<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(mapList, true);
        return responseEnv;
    }


    @PostMapping(value = "/core/customer")
    public ResponseEnvelope<Long> createCustomer(@RequestBody CustomerVO customerVO) {
        CustomerModel customerModel = beanMapper.map(customerVO, CustomerModel.class);
        customerService.createSelective(customerModel);
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(customerModel.getId(), true);
        return responseEnv;
    }


    @PutMapping(value = "/core/user/phone")
    public ResponseEnvelope<Long> updatePhone(@RequestHeader("Authorization") String authorization,
                                              @RequestBody Map<String, String> requestParam) {
        String phone = requestParam.get("phone");
        String code = validateCodeCache.getIfPresent(phone);
        if (StringUtils.isEmpty(code)) {
            HRErrorCode.throwBusinessException(HRErrorCode.CODE_NOT_EXISTED);
        }

        if (!code.equals(requestParam.get("code"))) {
            HRErrorCode.throwBusinessException(HRErrorCode.CODE_ERROR);
        }

        SessionUserModel sessionUserModel = sessionCache.getIfPresent(authorization);
        if (SessionUserModel.ROLE_DOCTOR == sessionUserModel.getRole()) {
            DoctorModel doctorModel = new DoctorModel();
            doctorModel.setId(sessionUserModel.getUserId());
            doctorModel.setPhone(phone);
            doctorService.updateByPrimaryKeySelective(doctorModel);

        } else if (SessionUserModel.ROLE_PATIENT == sessionUserModel.getRole()) {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setId(sessionUserModel.getUserId());
            customerModel.setPhone(phone);
            customerService.updateByPrimaryKeySelective(customerModel);
        }


        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(1L, true);
        return responseEnv;
    }

    @DeleteMapping(value = "/core/customer/{id}")
    public ResponseEnvelope<Integer> deleteCustomerByPrimaryKey(@PathVariable Long id) {
        Integer result = customerService.deleteByPrimaryKey(id);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }


    @PutMapping(value = "/core/customer/{id}")
    public ResponseEnvelope<Integer> updateCustomerByPrimaryKeySelective(@PathVariable Long id,
                                                                         @RequestBody CustomerVO customerVO) {
        CustomerModel customerModel = beanMapper.map(customerVO, CustomerModel.class);
        customerModel.setId(id);
        Integer result = customerService.updateByPrimaryKeySelective(customerModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }


    @IgnoreAuth
    @GetMapping(value = "/customer/validate")
    public ResponseEnvelope<Integer> customerValidate(@RequestParam("phone") String phone) {
//
//        String validateCode = RandomUtil.createRandom(true,4);
        String validateCode = "123456";
        validateCodeCache.put(phone, validateCode);
//        smsService.sendSms(templateCode, phone, ImmutableMap.of("name", validateCode));

        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }


    @IgnoreAuth
    @PostMapping(value = "/customer/login")
    public ResponseEnvelope<CustomerVO> customerLogin(@RequestBody Map<String, String> requestParam) {

        //验证码
        String phone = requestParam.get("phone");
        String code = validateCodeCache.getIfPresent(phone);
        if (StringUtils.isEmpty(code)) {
            HRErrorCode.throwBusinessException(HRErrorCode.CODE_NOT_EXISTED);
        }

        if (!code.equals(requestParam.get("code"))) {
            HRErrorCode.throwBusinessException(HRErrorCode.CODE_ERROR);
        }

        //判断用户存在否
        CustomerModel param = new CustomerModel();
        param.setPhone(phone);
        List<CustomerModel> customerModels = customerService.selectPage(param, new PageRequest(0, 1));
        CustomerModel customerModel = null;
        if (CollectionUtils.isEmpty(customerModels)) {
            //
            customerModel = new CustomerModel();
            customerModel.setPhone(phone);
            customerService.createSelective(customerModel);
        } else {
            customerModel = customerModels.get(0);
        }

        String sessionId = RandomUtil.generateAuthToken();
        sessionCache.put(sessionId, new SessionUserModel(customerModel.getId(), SessionUserModel.ROLE_PATIENT));

        CustomerVO customerVO = beanMapper.map(customerModel, CustomerVO.class);
        customerVO.setSessionId(sessionId);
        ResponseEnvelope<CustomerVO> responseEnv = new ResponseEnvelope<>(customerVO, true);
        return responseEnv;
    }
}
