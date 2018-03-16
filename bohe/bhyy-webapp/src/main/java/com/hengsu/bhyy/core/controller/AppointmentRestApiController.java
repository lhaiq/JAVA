package com.hengsu.bhyy.core.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
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

import com.hengsu.bhyy.core.service.AppointmentService;
import com.hengsu.bhyy.core.model.AppointmentModel;
import com.hengsu.bhyy.core.vo.AppointmentVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class AppointmentRestApiController {

	private final Logger logger = LoggerFactory.getLogger(AppointmentRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping(value = "/core/appointment/{id}")
	public ResponseEnvelope<AppointmentVO> getAppointmentById(@PathVariable Long id){
		AppointmentModel appointmentModel = appointmentService.findByPrimaryKey(id);
		AppointmentVO appointmentVO =beanMapper.map(appointmentModel, AppointmentVO.class);
		if(StringUtils.isNotEmpty(appointmentModel.getVisitPlans())){
			appointmentVO.setVisitPlanIds(JSON.parseArray(appointmentModel.getVisitPlans(),Long.class));
		}

		ResponseEnvelope<AppointmentVO> responseEnv = new ResponseEnvelope<>(appointmentVO,true);
		return responseEnv;
	}


	@GetMapping(value = "/core/appointment")
    public ResponseEnvelope<Page<AppointmentModel>> listAppointment(AppointmentVO appointmentVO,Pageable pageable){

		AppointmentModel param = beanMapper.map(appointmentVO, AppointmentModel.class);
        List<AppointmentModel> appointmentModelModels = appointmentService.selectPage(param,pageable);
        long count=appointmentService.selectCount(param);
        Page<AppointmentModel> page = new PageImpl<>(appointmentModelModels,pageable,count);
        ResponseEnvelope<Page<AppointmentModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }


	@GetMapping(value = "/core/doctor/appointment")
	public ResponseEnvelope<Page<Map<String,Object>>> listDoctorAppointment(@RequestAttribute("userId") Long userId,
																		  @RequestParam int status,
																		  Pageable pageable){

		Page<Map<String,Object>> page  = appointmentService.selectDoctorPage(userId,status,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/customer/appointment")
	public ResponseEnvelope<Page<Map<String,Object>>> listCustomerAppointment(@RequestAttribute("userId") Long userId,
																			@RequestParam int status,
																			Pageable pageable){

		Page<Map<String,Object>> page  = appointmentService.selectCustomerPage(userId,status,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@PostMapping(value = "/core/appointment/search")
	public ResponseEnvelope<Page<Map<String,Object>>> searchAppointment(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String,Object>> page = appointmentService.searchAppointPage(param,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}


	@PostMapping(value = "/core/visit/search")
	public ResponseEnvelope<Page<Map<String,Object>>> searchVisit(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String,Object>> page = appointmentService.searchVisitPage(param,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	/**
	 * 新增预约-后台
	 * @param appointmentVO
	 * @return
	 */
	@PostMapping(value = "/core/backend/appointment")
	public ResponseEnvelope<Integer> createAppointmentByBackend(@RequestBody AppointmentVO appointmentVO){
		AppointmentModel appointmentModel = beanMapper.map(appointmentVO, AppointmentModel.class);
		appointmentModel.setSource(AppointmentModel.SOURCE_BACKEDN);
		appointmentModel.setVisitPlans(JSON.toJSONString(appointmentVO.getVisitPlanIds()));
		Integer  result = appointmentService.createSelective(appointmentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
		return responseEnv;
	}



	/**
	 * 新增预约-app
	 * @param appointmentVO
	 * @return
	 */
	@PostMapping(value = "/core/app/appointment")
	public ResponseEnvelope<Integer> createAppointmentByApp(@RequestBody AppointmentVO appointmentVO){
		AppointmentModel appointmentModel = beanMapper.map(appointmentVO, AppointmentModel.class);
		appointmentModel.setSource(AppointmentModel.SOURCE_FROTEND);
		Integer  result = appointmentService.createSelective(appointmentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
		return responseEnv;
	}

    @DeleteMapping(value = "/core/appointment/{id}")
	public ResponseEnvelope<Integer> deleteAppointmentByPrimaryKey(@PathVariable Long id){
		Integer  result = appointmentService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/appointment/{id}")
	public ResponseEnvelope<Integer> updateAppointmentByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody AppointmentVO appointmentVO){
		AppointmentModel appointmentModel = beanMapper.map(appointmentVO, AppointmentModel.class);
		appointmentModel.setId(id);
		appointmentModel.setVisitPlans(JSON.toJSONString(appointmentVO.getVisitPlanIds()));
		Integer  result = appointmentService.updateByPrimaryKeySelective(appointmentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

}
