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

import com.hengsu.bhyy.core.service.DoctorInviteLogService;
import com.hengsu.bhyy.core.model.DoctorInviteLogModel;
import com.hengsu.bhyy.core.vo.DoctorInviteLogVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class DoctorInviteLogRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DoctorInviteLogRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorInviteLogService doctorInviteLogService;


//	/**
//	 * 医生邀请扫码
//	 * @param doctorInviteLogVO
//	 * @return
//     */
//	@PostMapping(value = "/core/doctorInvite/scan")
//	public ResponseEnvelope<Integer> createDoctorInviteLog(@RequestBody DoctorInviteLogVO doctorInviteLogVO){
//		DoctorInviteLogModel doctorInviteLogModel = beanMapper.map(doctorInviteLogVO, DoctorInviteLogModel.class);
//		Integer  result = doctorInviteLogService.createSelective(doctorInviteLogModel);
//		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
//        return responseEnv;
//	}
}
