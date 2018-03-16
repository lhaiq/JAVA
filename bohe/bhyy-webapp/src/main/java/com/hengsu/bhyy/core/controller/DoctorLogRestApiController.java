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

import com.hengsu.bhyy.core.service.DoctorLogService;
import com.hengsu.bhyy.core.model.DoctorLogModel;
import com.hengsu.bhyy.core.vo.DoctorLogVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class DoctorLogRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DoctorLogRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorLogService doctorLogService;


	@GetMapping(value = "/core/{doctorId}/doctorLog")
    public ResponseEnvelope<Page<DoctorLogModel>> listDoctorLog(
			@PathVariable("doctorId") Long doctorId,
			DoctorLogVO doctorLogVO,
			Pageable pageable){

		DoctorLogModel param = beanMapper.map(doctorLogVO, DoctorLogModel.class);
		param.setDoctorId(doctorId);
        List<DoctorLogModel> doctorLogModelModels = doctorLogService.selectPage(param,pageable);
        long count=doctorLogService.selectCount(param);
        Page<DoctorLogModel> page = new PageImpl<>(doctorLogModelModels,pageable,count);
        ResponseEnvelope<Page<DoctorLogModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

}
