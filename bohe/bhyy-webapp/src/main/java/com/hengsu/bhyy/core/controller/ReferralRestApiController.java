package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.model.DoctorModel;
import com.hengsu.bhyy.core.model.ReferralLogModel;
import com.hengsu.bhyy.core.model.ReferralModel;
import com.hengsu.bhyy.core.service.CustomerService;
import com.hengsu.bhyy.core.service.DoctorService;
import com.hengsu.bhyy.core.service.ReferralLogService;
import com.hengsu.bhyy.core.service.ReferralService;
import com.hengsu.bhyy.core.vo.ReferralLogVO;
import com.hengsu.bhyy.core.vo.ReferralSearchVO;
import com.hengsu.bhyy.core.vo.ReferralVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import scala.Int;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class ReferralRestApiController {

    private final Logger logger = LoggerFactory.getLogger(ReferralRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private ReferralLogService referralLogService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DoctorService doctorService;


    @GetMapping(value = "/core/referral/{id}")
    public ResponseEnvelope<ReferralVO> getReferralById(@PathVariable Long id) {
        ReferralModel referralModel = referralService.findByPrimaryKey(id);
        ReferralVO referralVO = beanMapper.map(referralModel, ReferralVO.class);
        ResponseEnvelope<ReferralVO> responseEnv = new ResponseEnvelope<>(referralVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/referral")
    public ResponseEnvelope<Page<ReferralModel>> listReferral(ReferralVO referralVO, Pageable pageable) {

        ReferralModel param = beanMapper.map(referralVO, ReferralModel.class);
        List<ReferralModel> referralModelModels = referralService.selectPage(param, pageable);
        long count = referralService.selectCount(param);
        Page<ReferralModel> page = new PageImpl<>(referralModelModels, pageable, count);
        ResponseEnvelope<Page<ReferralModel>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    /**
     * 搜索
     *
     * @param param
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/referral/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchReferral(@RequestBody Map<String, String> param, Pageable pageable) {
        Page<Map<String, Object>> page = referralService.searchAppointPage(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    /**
     * 转诊中
     * @param referralSearchVO
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/referraling")
    public ResponseEnvelope<Page<Map<String, Object>>> referraling(@RequestAttribute("userId") Long userId,
            @RequestBody ReferralSearchVO referralSearchVO, Pageable pageable) {
        Page<Map<String, Object>> page = referralService.selectReferralingPage(userId,referralSearchVO.getStatuses(),referralSearchVO.getStartDate(),
                referralSearchVO.getEndDate(), pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    /**
     * 转诊成功
     * @param referralSearchVO
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/referraled")
    public ResponseEnvelope<Map<String, Object>> referraled(@RequestAttribute("userId") Long userId,
            @RequestBody ReferralSearchVO referralSearchVO,  Pageable pageable) {
        Map<String, Object> map = referralService.selectReferraledPage(userId,referralSearchVO.getStatuses(),referralSearchVO.getStartDate(),
                referralSearchVO.getEndDate(), pageable);
        ResponseEnvelope<Map<String, Object>> responseEnv = new ResponseEnvelope<>(map, true);
        return responseEnv;
    }



    /**
     * 扫描
     *
     * @param referralVO
     * @return
     */
    @PostMapping(value = "/core/referral/scan")
    public ResponseEnvelope<Long> createReferral(@RequestBody ReferralVO referralVO) {
        ReferralModel referralModel = beanMapper.map(referralVO, ReferralModel.class);
        referralService.scan(referralModel);
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(referralModel.getId(), true);
        return responseEnv;
    }

    /**
     * 咨询
     *
     * @param referralVO
     * @return
     */
    @PostMapping(value = "/core/referral/consult")
    public ResponseEnvelope<Integer> consultReferral(@RequestBody ReferralVO referralVO) {
        referralService.consult(referralVO.getId());
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }


    /**
     * 生成报告
     * @param ids
     * @return
     */
    @PostMapping(value = "/core/referral/send")
    public ResponseEnvelope<Integer> sendReferral(@RequestBody List<Long> ids) {
        referralService.sendReport(ids);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping(value = "/core/referral/detail/{id}")
    public ResponseEnvelope<ReferralVO> detailReferral(@PathVariable Long id) {
        ReferralModel referralModel = referralService.findByPrimaryKey(id);
        ReferralVO referralVO = beanMapper.map(referralModel,ReferralVO.class);
        if(null!=referralModel.getCustomerId()){
            CustomerModel customerModel = customerService.findByPrimaryKey(referralModel.getCustomerId());
            referralVO.setCustomerName(customerModel.getRealName());
            referralVO.setCustomerPhone(customerModel.getPhone());
            referralVO.setCustomerIcon(customerModel.getIcon());
        }

        DoctorModel doctorModel = doctorService.findByPrimaryKey(referralModel.getDoctorId());
        referralVO.setDoctorName(doctorModel.getRealName());
        referralVO.setDoctorIcon(doctorModel.getDoctorIcon());
        ReferralLogModel param = new ReferralLogModel();
        param.setReferralId(id);
        List<ReferralLogModel> referralLogModels = referralLogService.selectPage(param,new PageRequest(0,Integer.MAX_VALUE, Sort.Direction.DESC,"create_time"));
        List<ReferralLogVO> referralLogVOs = beanMapper.mapAsList(referralLogModels,ReferralLogVO.class);
        referralVO.setLogs(referralLogVOs);
        ResponseEnvelope<ReferralVO> responseEnv = new ResponseEnvelope<>(referralVO, true);
        return responseEnv;
    }

    @DeleteMapping(value = "/core/referral/{id}")
    public ResponseEnvelope<Integer> deleteReferralByPrimaryKey(@PathVariable Long id) {
        Integer result = referralService.deleteByPrimaryKey(id);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }


    @PutMapping(value = "/core/referral/{id}")
    public ResponseEnvelope<Integer> updateReferralByPrimaryKeySelective(@PathVariable Long id,
                                                                         @RequestBody ReferralVO referralVO) {
        ReferralModel referralModel = beanMapper.map(referralVO, ReferralModel.class);
        referralModel.setId(id);
        Integer result = referralService.updateByPrimaryKeySelective(referralModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }

}
