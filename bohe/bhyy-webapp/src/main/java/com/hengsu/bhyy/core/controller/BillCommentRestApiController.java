package com.hengsu.bhyy.core.controller;

import com.alibaba.fastjson.JSON;
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

import com.hengsu.bhyy.core.service.BillCommentService;
import com.hengsu.bhyy.core.model.BillCommentModel;
import com.hengsu.bhyy.core.vo.BillCommentVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class BillCommentRestApiController {

	private final Logger logger = LoggerFactory.getLogger(BillCommentRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BillCommentService billCommentService;

	@GetMapping(value = "/core/{billId}/billComment")
	public ResponseEnvelope<BillCommentVO> getBillCommentByBillId(@PathVariable Long billId){
		BillCommentModel param = new BillCommentModel();
		param.setBillId(billId);
		List<BillCommentModel> billCommentModels = billCommentService.selectPage(param,new PageRequest(0,1));
		BillCommentModel billCommentModel = null;
		if(CollectionUtils.isEmpty(billCommentModels)){
			billCommentModel = new BillCommentModel();
		}else {
			billCommentModel = billCommentModels.get(0);
			billCommentModel.setTagIds(JSON.parseArray(billCommentModel.getTags(),Long.class));
		}

		BillCommentVO billCommentVO =beanMapper.map(billCommentModel, BillCommentVO.class);
		ResponseEnvelope<BillCommentVO> responseEnv = new ResponseEnvelope<>(billCommentVO,true);
		return responseEnv;
	}


	@PostMapping(value = "/core/billComment")
	public ResponseEnvelope<Integer> createBillComment(@RequestBody BillCommentVO billCommentVO){
		BillCommentModel billCommentModel = beanMapper.map(billCommentVO, BillCommentModel.class);
		Integer  result = billCommentService.createSelective(billCommentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}



}
