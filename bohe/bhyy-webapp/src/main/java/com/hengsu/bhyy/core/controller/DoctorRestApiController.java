package com.hengsu.bhyy.core.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.collect.ImmutableMap;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.annotation.IgnoreAuth;
import com.hengsu.bhyy.core.entity.DoctorRecommend;
import com.hengsu.bhyy.core.model.*;
import com.hengsu.bhyy.core.service.*;
import com.hengsu.bhyy.core.vo.DoctorSearchVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import com.wlw.pylon.web.rest.annotation.RestApiController;

import com.hengsu.bhyy.core.vo.DoctorVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private DoctorRecommendService doctorRecommendService;

    @Autowired
    private DoctorInviteLogService doctorInviteLogService;

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

            //新注册
            if (!requestParam.containsKey("doctorId")) {
                doctorModel = new DoctorModel();
                doctorModel.setPhone(phone);
                doctorModel.setSource(0);
                doctorService.createSelective(doctorModel);
            }
            //邀约医生
            else {
                Long doctorId = Long.parseLong(requestParam.get("doctorId").toString());
                doctorModel = new DoctorModel();
                doctorModel.setPhone(phone);
                doctorModel.setId(doctorId);
                doctorService.updateByPrimaryKeySelective(doctorModel);
                doctorInviteLogService.add(doctorId, "已注册");
            }


        } else {
            doctorModel = doctorModels.get(0);
        }

        String sessionId = RandomUtil.generateAuthToken();
        sessionCache.put(sessionId, new SessionUserModel(doctorModel.getId(), SessionUserModel.ROLE_DOCTOR));

        DoctorVO doctorVO = beanMapper.map(doctorModel, DoctorVO.class);
        doctorVO.setSessionId(sessionId);
        ResponseEnvelope<DoctorVO> responseEnv = new ResponseEnvelope<>(doctorVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/doctor/{id}")
    public ResponseEnvelope<DoctorVO> getDoctorById(@PathVariable Long id) {
        DoctorModel doctorModel = doctorService.findByPrimaryKey(id);
        DoctorVO doctorVO = beanMapper.map(doctorModel, DoctorVO.class);
        if (StringUtils.isNotEmpty(doctorModel.getAdept())) {
            doctorVO.setAdepts(JSON.parseArray(doctorModel.getAdept(), String.class));
        }
        if (StringUtils.isNotEmpty(doctorModel.getDepartment())) {
            doctorVO.setDepartments(JSON.parseArray(doctorModel.getDepartment(), String.class));
        }
        if (StringUtils.isNotEmpty(doctorModel.getServiceItem())) {
            doctorVO.setServiceItems(JSON.parseArray(doctorModel.getServiceItem(), String.class));
        }

        DoctorRecommendModel param = new DoctorRecommendModel();
        param.setPresenter(id);
        List<DoctorRecommendModel> doctorRecommendModels = doctorRecommendService.selectPage(param,new PageRequest(0,Integer.MAX_VALUE));
        if(CollectionUtils.isNotEmpty(doctorRecommendModels)){
            List<Long> doctorRecommends = doctorRecommendModels.stream().map(e->e.getPresentee()).collect(Collectors.toList());
            doctorVO.setDoctorRecommends(doctorRecommends);
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
        if (CollectionUtils.isNotEmpty(doctorVO.getAdepts())) {
            doctorModel.setAdept(JSON.toJSONString(doctorVO.getAdepts()));
        }

        if (CollectionUtils.isNotEmpty(doctorVO.getDepartments())) {
            doctorModel.setDepartment(JSON.toJSONString(doctorVO.getDepartments()));
        }

        if (CollectionUtils.isNotEmpty(doctorVO.getServiceItems())) {
            doctorModel.setServiceItem(JSON.toJSONString(doctorVO.getServiceItems()));
        }
        doctorModel.setAddDate(new Date());
        doctorModel.setSource(2);
        Integer result = doctorService.createSelective(doctorModel);

        doctorRecommendService.addRecommends(doctorModel.getId(), doctorVO.getDoctorRecommends());

        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(doctorModel.getId(), true);
        return responseEnv;
    }


    @PostMapping(value = "/core/doctor/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchDoctor(@RequestBody Map<String, Object> param,
                                                                    Pageable pageable) {
        Page<Map<String, Object>> page = doctorService.searchPage(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    @PostMapping(value = "/core/doctor/search/app")
    public ResponseEnvelope<Page<Map<String, Object>>> searchDoctorForApp(@RequestBody DoctorSearchVO doctorSearchVO,
                                                                          Pageable pageable) {
        Page<Map<String, Object>> page = doctorService.searchPageForApp(doctorSearchVO.getDayOfWeek(),
                doctorSearchVO.getName(), doctorSearchVO.getItemName(),doctorSearchVO.getIsRecommend(), pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    /**
     * 医生扫码
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/core/doctor/scan")
    public ResponseEnvelope<Long> scan(@RequestParam Long id) {
        Long resultId = doctorService.scan(id);
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(resultId, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/doctor/inviteDetail")
    public ResponseEnvelope<Map<String, Object>> inviteDetail(@RequestParam Long id) {
        Map<String, Object> map = new HashMap<>();
        DoctorModel doctorModel = doctorService.findByPrimaryKey(id);
        map.putAll(ImmutableMap.of("id", doctorModel.getId(), "name", doctorModel.getRealName(),
                "phone", doctorModel.getPhone(), "status", doctorModel.getStatus()));
        if (null != doctorModel.getInviteId()) {
            DoctorModel inviteDoctor = doctorService.findByPrimaryKey(doctorModel.getInviteId());
            map.put("inviteName", inviteDoctor.getRealName());
            DoctorInviteLogModel param = new DoctorInviteLogModel();
            param.setDoctorId(id);
            PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "create_time");
            List<DoctorInviteLogModel> doctorInviteLogModels = doctorInviteLogService.selectPage(param, pageRequest);
            map.put("log", doctorInviteLogModels);
        }
        ResponseEnvelope<Map<String, Object>> responseEnv = new ResponseEnvelope<>(map, true);
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
        if (CollectionUtils.isNotEmpty(doctorVO.getAdepts())) {
            doctorModel.setAdept(JSON.toJSONString(doctorVO.getAdepts()));
        }

        if (CollectionUtils.isNotEmpty(doctorVO.getDepartments())) {
            doctorModel.setDepartment(JSON.toJSONString(doctorVO.getDepartments()));
        }

        if (CollectionUtils.isNotEmpty(doctorVO.getServiceItems())) {
            doctorModel.setServiceItem(JSON.toJSONString(doctorVO.getServiceItems()));
        }

        Integer result = doctorService.updateByPrimaryKeySelective(doctorModel);

        doctorRecommendService.addRecommends(doctorModel.getId(), doctorVO.getDoctorRecommends());

        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }


}
