package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.entity.Bill;
import com.hengsu.bhyy.core.model.*;
import com.hengsu.bhyy.core.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.hengsu.bhyy.core.vo.CaseReportVO;

import javax.ws.rs.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class CaseReportRestApiController {

    private final Logger logger = LoggerFactory.getLogger(CaseReportRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CaseReportService caseReportService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private BillService billService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private CaseCategoryService caseCategoryService;

    @GetMapping(value = "/core/caseReport/{id}")
    public ResponseEnvelope<CaseReportVO> getCaseReportById(@PathVariable Long id) {
        CaseReportModel caseReportModel = caseReportService.findByPrimaryKey(id);
        CaseReportVO caseReportVO = beanMapper.map(caseReportModel, CaseReportVO.class);
        fillNames(caseReportVO);
        ResponseEnvelope<CaseReportVO> responseEnv = new ResponseEnvelope<>(caseReportVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/caseReport")
    public ResponseEnvelope<Page<CaseReportModel>> listCaseReport(CaseReportVO caseReportVO, Pageable pageable) {

        CaseReportModel param = beanMapper.map(caseReportVO, CaseReportModel.class);
        List<CaseReportModel> caseReportModelModels = caseReportService.selectPage(param, pageable);
        long count = caseReportService.selectCount(param);
        Page<CaseReportModel> page = new PageImpl<>(caseReportModelModels, pageable, count);
        ResponseEnvelope<Page<CaseReportModel>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    /**
     * 添加病历
     *
     * @param caseReportVO
     * @return
     */
    @PostMapping(value = "/core/caseReport")
    public ResponseEnvelope<Integer> createCaseReport(@RequestBody CaseReportVO caseReportVO) {
        CaseReportModel caseReportModel = beanMapper.map(caseReportVO, CaseReportModel.class);
        caseReportModel.setCreateTime(new Date());
        if (StringUtils.isEmpty(caseReportVO.getAppointStrId())) {
            HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("1222", "预约id不能为空"));
        }
        AppointmentModel param = new AppointmentModel();
        param.setAppointId(caseReportVO.getAppointStrId());
        List<AppointmentModel> appointmentModels = appointmentService.selectPage(param, new PageRequest(0, Integer.MAX_VALUE));
        if (CollectionUtils.isEmpty(appointmentModels)) {
            HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("1222", "预约不存在"));
        }

        Long appointmentId = appointmentModels.get(0).getId();
        BillModel billParam = new BillModel();

        List<BillModel> billModels = billService.selectPage(billParam, new PageRequest(0, Integer.MAX_VALUE));
        if (CollectionUtils.isEmpty(billModels)) {
            HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("1222", "定单不存在"));
        }

        caseReportModel.setBillId(billModels.get(0).getId());
        caseReportModel.setAppointId(appointmentId);
        caseReportModel.setPatientId(appointmentModels.get(0).getPatientId());
        Integer result = caseReportService.createSelective(caseReportModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }


    /**
     * 患者搜索
     */
    @PostMapping(value = "/core/caseReport/customer/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchCustomerCaseReport(@RequestAttribute("userId") Long userId,
                                                                              Pageable pageable) {
        Page<Map<String, Object>> page = caseReportService.searchCustomerCaseReport(userId, new HashMap<>(), pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    /**
     * 病历更新
     *
     * @param id
     * @param caseReportVO
     * @return
     */
    @PutMapping(value = "/core/caseReport/{id}")
    public ResponseEnvelope<Long> updateCaseReport(@PathVariable Long id,
                                                   @RequestBody CaseReportVO caseReportVO) {
        CaseReportModel param = beanMapper.map(caseReportVO, CaseReportModel.class);
        param.setId(id);
        caseReportService.updateByPrimaryKeySelective(param);
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(1L, true);
        return responseEnv;
    }


    /**
     * 病历搜索-医生
     *
     * @param param
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/caseReport/doctor/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchDoctorCaseReport(@RequestBody Map<String, Object> param,
                                                                              @RequestAttribute("userId") Long userId,
                                                                              Pageable pageable) {
        Page<Map<String, Object>> page = caseReportService.searchDoctorCaseReport(userId, param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    /**
     * 病历搜索-后台
     *
     * @param param
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/caseReport/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchCaseReport(@RequestBody Map<String, Object> param,
                                                                        Pageable pageable) {
        Page<Map<String, Object>> page = caseReportService.searchCaseReport(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    /**
     * 初筛
     *
     * @param caseReportVO
     * @return
     */
    @PostMapping(value = "/core/caseReport/primaryScreen")
    public ResponseEnvelope<Integer> createCaseReportPrimaryScreen(@RequestBody CaseReportVO caseReportVO) {
        CaseReportModel caseReportModel = beanMapper.map(caseReportVO, CaseReportModel.class);
        caseReportModel.setCreateTime(new Date());
        caseReportModel.setCaseType(0);
        Integer result = caseReportService.createSelective(caseReportModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }

    /**
     * 初筛更新
     *
     * @param id
     * @param caseReportVO
     * @return
     */
    @PutMapping(value = "/core/caseReport/primaryScreen/{id}")
    public ResponseEnvelope<Integer> updateCaseReportPrimaryScreen(@PathVariable Long id,
                                                                   @RequestBody CaseReportVO caseReportVO) {
        CaseReportModel caseReportModel = beanMapper.map(caseReportVO, CaseReportModel.class);
        caseReportModel.setId(id);
        caseReportService.updateByPrimaryKeySelective(caseReportModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }


    /**
     * 初筛搜索
     *
     * @param param
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/caseReport/primaryScreen/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchCaseReportPrimaryScreen(@RequestBody Map<String, Object> param,
                                                                                     Pageable pageable) {
        Page<Map<String, Object>> page = caseReportService.primaryScreenSearch(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    @PutMapping(value = "/core/caseReport")
    public ResponseEnvelope<Integer> searchCaseReportPrimaryScreen(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            CaseReportModel param = new CaseReportModel();
            param.setId(id);
            param.setIsSend(1);
            caseReportService.updateByPrimaryKeySelective(param);
        }

        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }

    @DeleteMapping(value = "/core/caseReport/{id}")
    public ResponseEnvelope<Long> deleteCaseReport(@PathVariable Long id) {
        caseReportService.deleteByPrimaryKey(id);
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(1L, true);
        return responseEnv;
    }


    private void fillNames(CaseReportVO caseReportVO) {
        CustomerModel customerModel = customerService.findByPrimaryKey(caseReportVO.getCustomerId());
        DoctorModel doctorModel = doctorService.findByPrimaryKey(caseReportVO.getDoctorId());
        HospitalModel hospitalModel = hospitalService.findByPrimaryKey(caseReportVO.getHospitalId());
        caseReportVO.setCustomerName(customerModel.getRealName());
        caseReportVO.setDoctorName(doctorModel.getRealName());
        caseReportVO.setCustomerPhone(customerModel.getPhone());
        caseReportVO.setDoctorPhone(doctorModel.getPhone());
        caseReportVO.setHospitalName(hospitalModel.getName());

        CaseCategoryModel three = caseCategoryService.findByPrimaryKey(caseReportVO.getCategoryId());
        CaseCategoryModel second = caseCategoryService.findByPrimaryKey(three.getParentId());
        caseReportVO.setSecondCategoryId(second.getId());
        caseReportVO.setFirstCategoryId(second.getParentId());
    }


}
