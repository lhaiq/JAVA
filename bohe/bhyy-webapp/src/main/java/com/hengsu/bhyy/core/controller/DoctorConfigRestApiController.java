package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.model.DoctorVisitPlanModel;
import com.hengsu.bhyy.core.model.HospitalModel;
import com.hengsu.bhyy.core.service.DoctorVisitPlanService;
import com.hengsu.bhyy.core.service.HospitalService;
import com.hengsu.bhyy.core.vo.DoctorVisitPlanVO;
import org.apache.commons.collections4.CollectionUtils;
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

import com.hengsu.bhyy.core.service.DoctorConfigService;
import com.hengsu.bhyy.core.model.DoctorConfigModel;
import com.hengsu.bhyy.core.vo.DoctorConfigVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class DoctorConfigRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DoctorConfigRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorConfigService doctorConfigService;

	@Autowired
	private DoctorVisitPlanService doctorVisitPlanService;

	@Autowired
	private HospitalService hospitalService;

	@GetMapping(value = "/core/doctorConfig/{id}")
	public ResponseEnvelope<DoctorConfigVO> getDoctorConfigById(@PathVariable Long id){
		DoctorConfigModel doctorConfigModel = doctorConfigService.findByPrimaryKey(id);
		DoctorConfigVO doctorConfigVO =beanMapper.map(doctorConfigModel, DoctorConfigVO.class);
		ResponseEnvelope<DoctorConfigVO> responseEnv = new ResponseEnvelope<>(doctorConfigVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/doctorConfig")
    public ResponseEnvelope<Page<DoctorConfigModel>> listDoctorConfig(DoctorConfigVO doctorConfigVO,Pageable pageable){

		DoctorConfigModel param = beanMapper.map(doctorConfigVO, DoctorConfigModel.class);
        List<DoctorConfigModel> doctorConfigModelModels = doctorConfigService.selectPage(param,pageable);
		List<Long> ids = new ArrayList<>();
		for(DoctorConfigModel doctorConfigModel:doctorConfigModelModels){
			ids.add(doctorConfigModel.getHospitalId());
		}

		if(CollectionUtils.isNotEmpty(ids)){
			List<HospitalModel> hospitalModels = hospitalService.selectByIds(ids);
			Map<Long,String> hospitalNames = new HashMap<>();
			for(HospitalModel hospitalModel:hospitalModels){
				hospitalNames.put(hospitalModel.getId(),hospitalModel.getName());
			}

			for(DoctorConfigModel doctorConfigModel:doctorConfigModelModels){
				doctorConfigModel.setHospitalName(hospitalNames.get(doctorConfigModel.getHospitalId()));
			}
		}


        long count=doctorConfigService.selectCount(param);
        Page<DoctorConfigModel> page = new PageImpl<>(doctorConfigModelModels,pageable,count);
        ResponseEnvelope<Page<DoctorConfigModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@GetMapping(value = "/core/{doctorId}/doctorConfig")
	public ResponseEnvelope<DoctorConfigVO> getDoctorByDay(@PathVariable Long doctorId,
														   @RequestParam String date){

		DoctorConfigModel param = new DoctorConfigModel();
		param.setDoctorId(doctorId);
		param.setDate(date);
		List<DoctorConfigModel> doctorConfigModelModels = doctorConfigService.selectPage(param,new PageRequest(0,1));
		if(CollectionUtils.isEmpty(doctorConfigModelModels)){
			HRErrorCode.throwBusinessException(HRErrorCode.CONFIG_NOT_EXISTED_ERROR);
		}
		DoctorConfigModel doctorConfigModel = doctorConfigModelModels.get(0);
		DoctorVisitPlanModel visitPlanParam = new DoctorVisitPlanModel();
		visitPlanParam.setConfigId(doctorConfigModel.getId());
		List<DoctorVisitPlanModel> doctorVisitPlanModels = doctorVisitPlanService.selectPage(visitPlanParam,new PageRequest(0,Integer.MAX_VALUE));
		List<DoctorVisitPlanVO> visitPlans = beanMapper.mapAsList(doctorVisitPlanModels,DoctorVisitPlanVO.class);
		DoctorConfigVO doctorConfigVO = beanMapper.map(doctorConfigModel,DoctorConfigVO.class);
		doctorConfigVO.setVisitPlans(visitPlans);
		ResponseEnvelope<DoctorConfigVO> responseEnv = new ResponseEnvelope<>(doctorConfigVO,true);
		return responseEnv;
	}

	//出诊安排
	@PostMapping(value = "/core/{doctorId}/doctorConfig")
	public ResponseEnvelope<Integer> createDoctorConfig(@RequestBody DoctorConfigVO doctorConfigVO){
		DoctorConfigModel doctorConfigModel = beanMapper.map(doctorConfigVO, DoctorConfigModel.class);

		//先查询改日期,医生是否有出诊安排,有则更新,无则添加
		DoctorConfigModel param = new DoctorConfigModel();
		param.setDate(doctorConfigVO.getDate());
		List<DoctorConfigModel> doctorConfigModels = doctorConfigService.selectPage(param,new PageRequest(0,1));
		if(CollectionUtils.isNotEmpty(doctorConfigModels)){
			doctorConfigModel.setId(doctorConfigModels.get(0).getId());
			doctorConfigService.updateByPrimaryKeySelective(doctorConfigModel);
			doctorVisitPlanService.updatePlan(doctorConfigModel);
		}else {
			doctorConfigService.createSelective(doctorConfigModel);
			doctorVisitPlanService.addPlan(doctorConfigModel);
		}


		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/doctorConfig/{id}")
	public ResponseEnvelope<Integer> deleteDoctorConfigByPrimaryKey(@PathVariable Long id){
		DoctorVisitPlanModel param = new DoctorVisitPlanModel();
		param.setStatus(DoctorVisitPlanModel.USED);
		param.setConfigId(id);
		long count = doctorVisitPlanService.selectCount(param);
		if(count>0){
			HRErrorCode.throwBusinessException(HRErrorCode.CONNOT_DELETE_CONFIG);
		}

		param.setStatus(null);
		doctorVisitPlanService.deleteSelective(param);
		Integer  result = doctorConfigService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


	@GetMapping(value = "/core/doctorConfig/name")
	public ResponseEnvelope<List<Map<String, Object>>> selectConfigByName(@RequestParam("name") String name) throws Exception{
		name = new String(name.getBytes("iso-8859-1"),"utf-8");
		List<Map<String, Object>> mapList = doctorConfigService.selectConfigByName(name);
		ResponseEnvelope<List<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(mapList,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/doctorConfig/id")
	public ResponseEnvelope<List<Map<String, Object>>> selectConfigByName(@RequestParam("id") Long id) throws Exception{
		List<Map<String, Object>> mapList = doctorConfigService.selectConfigById(id);
		ResponseEnvelope<List<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(mapList,true);
		return responseEnv;
	}
}
