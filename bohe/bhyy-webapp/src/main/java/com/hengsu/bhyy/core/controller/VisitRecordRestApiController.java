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

import com.hengsu.bhyy.core.service.VisitRecordService;
import com.hengsu.bhyy.core.model.VisitRecordModel;
import com.hengsu.bhyy.core.vo.VisitRecordVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class VisitRecordRestApiController {

	private final Logger logger = LoggerFactory.getLogger(VisitRecordRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private VisitRecordService visitRecordService;

	@GetMapping(value = "/core/visitRecord/{id}")
	public ResponseEnvelope<VisitRecordVO> getVisitRecordById(@PathVariable Long id){
		VisitRecordModel visitRecordModel = visitRecordService.findByPrimaryKey(id);
		VisitRecordVO visitRecordVO =beanMapper.map(visitRecordModel, VisitRecordVO.class);
		ResponseEnvelope<VisitRecordVO> responseEnv = new ResponseEnvelope<>(visitRecordVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/{appointId}/visitRecord")
    public ResponseEnvelope<List<Map<String, Object>>> selectByAppointId(@PathVariable Long appointId){
		List<Map<String, Object>> maps = visitRecordService.selectByAppointId(appointId);
        ResponseEnvelope<List<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(maps,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/visitRecord")
	public ResponseEnvelope<Integer> createVisitRecord(@RequestBody VisitRecordVO visitRecordVO){
		VisitRecordModel visitRecordModel = beanMapper.map(visitRecordVO, VisitRecordModel.class);
		Integer  result = visitRecordService.createSelective(visitRecordModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/visitRecord/{id}")
	public ResponseEnvelope<Integer> deleteVisitRecordByPrimaryKey(@PathVariable Long id){
		Integer  result = visitRecordService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/visitRecord/{id}")
	public ResponseEnvelope<Integer> updateVisitRecordByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody VisitRecordVO visitRecordVO){
		VisitRecordModel visitRecordModel = beanMapper.map(visitRecordVO, VisitRecordModel.class);
		visitRecordModel.setId(id);
		Integer  result = visitRecordService.updateByPrimaryKeySelective(visitRecordModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
