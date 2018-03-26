package com.hengsu.bhyy.core.controller;

import com.alibaba.fastjson.JSON;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.model.BillModel;
import com.hengsu.bhyy.core.service.BillService;
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

	@Autowired
	private BillService billService;

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
	public ResponseEnvelope<Integer> createBillComment(@RequestAttribute("userId") Long userId,
			@RequestBody BillCommentVO billCommentVO){
		BillCommentModel billCommentModel = beanMapper.map(billCommentVO, BillCommentModel.class);
		BillModel billModel = billService.findByPrimaryKey(billCommentModel.getBillId());
		if(billModel.getIsComment()==1){
			HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("6000","已评论"));
		}

		if(!userId.equals(billModel.getCustomerId())){
			HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("6000","不是你的订单"));
		}


		Integer  result = billCommentService.createSelective(billCommentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}



}
