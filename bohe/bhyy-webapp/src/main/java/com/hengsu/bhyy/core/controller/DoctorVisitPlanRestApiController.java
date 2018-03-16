package com.hengsu.bhyy.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.hengsu.bhyy.core.service.DoctorVisitPlanService;
import com.hengsu.bhyy.core.model.DoctorVisitPlanModel;
import com.hengsu.bhyy.core.vo.DoctorVisitPlanVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class DoctorVisitPlanRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DoctorVisitPlanRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorVisitPlanService doctorVisitPlanService;

	@GetMapping(value = "/core/doctorVisitPlan/{id}")
	public ResponseEnvelope<DoctorVisitPlanVO> getDoctorVisitPlanById(@PathVariable Long id){
		DoctorVisitPlanModel doctorVisitPlanModel = doctorVisitPlanService.findByPrimaryKey(id);
		DoctorVisitPlanVO doctorVisitPlanVO =beanMapper.map(doctorVisitPlanModel, DoctorVisitPlanVO.class);
		ResponseEnvelope<DoctorVisitPlanVO> responseEnv = new ResponseEnvelope<>(doctorVisitPlanVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/doctorVisitPlan")
    public ResponseEnvelope<Page<DoctorVisitPlanModel>> listDoctorVisitPlan(DoctorVisitPlanVO doctorVisitPlanVO,Pageable pageable){

		DoctorVisitPlanModel param = beanMapper.map(doctorVisitPlanVO, DoctorVisitPlanModel.class);
        List<DoctorVisitPlanModel> doctorVisitPlanModelModels = doctorVisitPlanService.selectPage(param,pageable);
        long count=doctorVisitPlanService.selectCount(param);
        Page<DoctorVisitPlanModel> page = new PageImpl<>(doctorVisitPlanModelModels,pageable,count);
        ResponseEnvelope<Page<DoctorVisitPlanModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/doctorVisitPlan")
	public ResponseEnvelope<Integer> createDoctorVisitPlan(@RequestBody DoctorVisitPlanVO doctorVisitPlanVO){
		DoctorVisitPlanModel doctorVisitPlanModel = beanMapper.map(doctorVisitPlanVO, DoctorVisitPlanModel.class);
		Integer  result = doctorVisitPlanService.create(doctorVisitPlanModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/doctorVisitPlan/{id}")
	public ResponseEnvelope<Integer> deleteDoctorVisitPlanByPrimaryKey(@PathVariable Long id){
		Integer  result = doctorVisitPlanService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/doctorVisitPlan/{id}")
	public ResponseEnvelope<Integer> updateDoctorVisitPlanByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody DoctorVisitPlanVO doctorVisitPlanVO){
		DoctorVisitPlanModel doctorVisitPlanModel = beanMapper.map(doctorVisitPlanVO, DoctorVisitPlanModel.class);
		doctorVisitPlanModel.setId(id);
		Integer  result = doctorVisitPlanService.updateByPrimaryKeySelective(doctorVisitPlanModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
