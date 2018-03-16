package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.model.OpinionModel;
import com.hengsu.bhyy.core.service.OpinionService;
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

import com.hengsu.bhyy.core.service.OpinionRecordService;
import com.hengsu.bhyy.core.model.OpinionRecordModel;
import com.hengsu.bhyy.core.vo.OpinionRecordVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class OpinionRecordRestApiController {

	private final Logger logger = LoggerFactory.getLogger(OpinionRecordRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private OpinionRecordService opinionRecordService;

	@Autowired
	private OpinionService opinionService;


	@GetMapping(value = "/core/opinionRecord")
    public ResponseEnvelope<List<Map<String, Object>>> selectByOpinionId(@RequestParam Long opinionId){
		List<Map<String, Object>> content = opinionRecordService.selectByOpinionId(opinionId);
        ResponseEnvelope<List<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(content,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/opinionRecord")
	public ResponseEnvelope<Integer> createOpinionRecord(@RequestBody OpinionRecordVO opinionRecordVO){
		OpinionRecordModel opinionRecordModel = beanMapper.map(opinionRecordVO, OpinionRecordModel.class);
		opinionRecordModel.setCreateTime(new Date());
		Integer  result = opinionRecordService.createSelective(opinionRecordModel);
		if(null!=opinionRecordVO.getStatus()){
			OpinionModel opinionModel = new OpinionModel();
			opinionModel.setId(opinionRecordVO.getOpinionId());
			opinionModel.setStatus(opinionRecordVO.getStatus());
			opinionService.updateByPrimaryKeySelective(opinionModel);
		}
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/opinionRecord/{id}")
	public ResponseEnvelope<Integer> deleteOpinionRecordByPrimaryKey(@PathVariable Long id){
		Integer  result = opinionRecordService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


}
