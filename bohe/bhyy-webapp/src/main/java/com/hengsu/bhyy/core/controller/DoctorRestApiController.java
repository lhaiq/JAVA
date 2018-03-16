package com.hengsu.bhyy.core.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.collect.ImmutableMap;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.annotation.IgnoreAuth;
import com.hengsu.bhyy.core.model.DoctorLogModel;
import com.hengsu.bhyy.core.model.SessionUserModel;
import com.hengsu.bhyy.core.service.DoctorLogService;
import com.hengsu.bhyy.core.service.SmsService;
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

import com.hengsu.bhyy.core.service.DoctorService;
import com.hengsu.bhyy.core.model.DoctorModel;
import com.hengsu.bhyy.core.vo.DoctorVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class DoctorRestApiController {

    private final Logger logger = LoggerFactory.getLogger(DoctorRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorLogService doctorLogService;

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


    @IgnoreAuth
    @GetMapping(value = "/doctor/validate")
    public ResponseEnvelope<Integer> doctorValidate(@RequestParam("phone") String phone) {

//        String validateCode = RandomUtil.createRandom(true,4);
        String validateCode = "123456";
        validateCodeCache.put(phone, validateCode);
//        smsService.sendSms(templateCode,phone,ImmutableMap.of("name",validateCode));

        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }


    @IgnoreAuth
    @PostMapping(value = "/doctor/login")
    public ResponseEnvelope<DoctorVO> doctorLogin(@RequestBody Map<String, String> requestParam) {

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
        DoctorModel param = new DoctorModel();
        param.setPhone(phone);
        List<DoctorModel> doctorModels = doctorService.selectPage(param, new PageRequest(0, 1));
        DoctorModel doctorModel = null;
        if (CollectionUtils.isEmpty(doctorModels)) {
            //
            doctorModel = new DoctorModel();
            doctorModel.setPhone(phone);
            doctorService.createSelective(doctorModel);

        } else {
            doctorModel = doctorModels.get(0);
        }

        String sessionId = RandomUtil.generateAuthToken();
        sessionCache.put(sessionId, new SessionUserModel(doctorModel.getId(),SessionUserModel.ROLE_DOCTOR));

        DoctorVO doctorVO = beanMapper.map(doctorModel, DoctorVO.class);
        doctorVO.setSessionId(sessionId);
        ResponseEnvelope<DoctorVO> responseEnv = new ResponseEnvelope<>(doctorVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/doctor/{id}")
    public ResponseEnvelope<DoctorVO> getDoctorById(@PathVariable Long id) {
        DoctorModel doctorModel = doctorService.findByPrimaryKey(id);
        DoctorVO doctorVO = beanMapper.map(doctorModel, DoctorVO.class);
        if(StringUtils.isNotEmpty(doctorModel.getAdept())){
            doctorVO.setAdepts(JSON.parseArray(doctorModel.getAdept(),String.class));
        }
        if(StringUtils.isNotEmpty(doctorModel.getDepartment())){
            doctorVO.setDepartments(JSON.parseArray(doctorModel.getDepartment(),String.class));
        }
        if(StringUtils.isNotEmpty(doctorModel.getServiceItem())){
            doctorVO.setServiceItems(JSON.parseArray(doctorModel.getServiceItem(),String.class));
        }
        ResponseEnvelope<DoctorVO> responseEnv = new ResponseEnvelope<>(doctorVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/doctor")
    public ResponseEnvelope<Page<DoctorModel>> listDoctor(DoctorVO doctorVO, Pageable pageable) {

        DoctorModel param = beanMapper.map(doctorVO, DoctorModel.class);
        List<DoctorModel> doctorModelModels = doctorService.selectPage(param, pageable);
        long count = doctorService.selectCount(param);
        Page<DoctorModel> page = new PageImpl<>(doctorModelModels, pageable, count);
        ResponseEnvelope<Page<DoctorModel>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    @PostMapping(value = "/core/doctor")
    public ResponseEnvelope<Long> createDoctor(@RequestBody DoctorVO doctorVO) {
        DoctorModel doctorModel = beanMapper.map(doctorVO, DoctorModel.class);
        if(CollectionUtils.isNotEmpty(doctorVO.getAdepts())){
            doctorModel.setAdept(JSON.toJSONString(doctorVO.getAdepts()));
        }

        if(CollectionUtils.isNotEmpty(doctorVO.getDepartments())){
            doctorModel.setDepartment(JSON.toJSONString(doctorVO.getDepartments()));
        }

        if(CollectionUtils.isNotEmpty(doctorVO.getServiceItems())){
            doctorModel.setServiceItem(JSON.toJSONString(doctorVO.getServiceItems()));
        }
        Integer result = doctorService.createSelective(doctorModel);
        doctorModel.setAddDate(new Date());
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(doctorModel.getId(), true);
        return responseEnv;
    }


    @PostMapping(value = "/core/doctor/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchDoctor(@RequestBody Map<String, String> param,
                                               Pageable pageable) {
        Page<Map<String, Object>> page = doctorService.searchPage(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>> > responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    @PostMapping(value = "/core/doctor/search/app")
    public ResponseEnvelope<Page<Map<String, Object>> > searchDoctorForApp(@RequestBody Map<String, String> param,
                                                     Pageable pageable) {
        Page<Map<String, Object>> page = doctorService.searchPageForApp(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>> > responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    //审核医生
    @PostMapping(value = "/core/check/{id}")
    public ResponseEnvelope<Integer> checkDoctor(@PathVariable Long id,
                                                 @RequestBody DoctorVO doctorVO) {
        DoctorModel doctorModel = beanMapper.map(doctorVO, DoctorModel.class);
        doctorModel.setId(id);

        //TODO 加分数

        if (DoctorModel.PASS == doctorVO.getStatus()) {
            doctorLogService.addDoctorLog(id, "提交的资料已经通过审核");
        } else if (DoctorModel.REFUSE == doctorVO.getStatus()) {
            doctorModel.setFailureTime(new Date());
            doctorLogService.addDoctorLog(id, "提交的资料未通过审核,原因:" + doctorVO.getFailureReason());
        }

        Integer result = doctorService.updateByPrimaryKeySelective(doctorModel);

        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }

    @PutMapping(value = "/core/doctor/{id}")
    public ResponseEnvelope<Integer> updateDoctor(@PathVariable Long id,
                                                  @RequestBody DoctorVO doctorVO) {
        DoctorModel doctorModel = beanMapper.map(doctorVO, DoctorModel.class);
        //绝对不能更新自己的余额
        doctorModel.setBalance(null);
        doctorModel.setId(id);
        if(CollectionUtils.isNotEmpty(doctorVO.getAdepts())){
            doctorModel.setAdept(JSON.toJSONString(doctorVO.getAdepts()));
        }

        if(CollectionUtils.isNotEmpty(doctorVO.getDepartments())){
            doctorModel.setDepartment(JSON.toJSONString(doctorVO.getDepartments()));
        }

        if(CollectionUtils.isNotEmpty(doctorVO.getServiceItems())){
            doctorModel.setServiceItem(JSON.toJSONString(doctorVO.getServiceItems()));
        }

        Integer result = doctorService.updateByPrimaryKeySelective(doctorModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }



}
