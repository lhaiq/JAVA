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

import com.hengsu.bhyy.core.service.ReferralLogService;
import com.hengsu.bhyy.core.model.ReferralLogModel;
import com.hengsu.bhyy.core.vo.ReferralLogVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class ReferralLogRestApiController {

	private final Logger logger = LoggerFactory.getLogger(ReferralLogRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ReferralLogService referralLogService;

	@GetMapping(value = "/core/referralLog/{id}")
	public ResponseEnvelope<ReferralLogVO> getReferralLogById(@PathVariable Long id){
		ReferralLogModel referralLogModel = referralLogService.findByPrimaryKey(id);
		ReferralLogVO referralLogVO =beanMapper.map(referralLogModel, ReferralLogVO.class);
		ResponseEnvelope<ReferralLogVO> responseEnv = new ResponseEnvelope<>(referralLogVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/referralLog")
    public ResponseEnvelope<Page<ReferralLogModel>> listReferralLog(ReferralLogVO referralLogVO,Pageable pageable){

		ReferralLogModel param = beanMapper.map(referralLogVO, ReferralLogModel.class);
        List<ReferralLogModel> referralLogModelModels = referralLogService.selectPage(param,pageable);
        long count=referralLogService.selectCount(param);
        Page<ReferralLogModel> page = new PageImpl<>(referralLogModelModels,pageable,count);
        ResponseEnvelope<Page<ReferralLogModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/referralLog")
	public ResponseEnvelope<Integer> createReferralLog(@RequestBody ReferralLogVO referralLogVO){
		ReferralLogModel referralLogModel = beanMapper.map(referralLogVO, ReferralLogModel.class);
		Integer  result = referralLogService.create(referralLogModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/referralLog/{id}")
	public ResponseEnvelope<Integer> deleteReferralLogByPrimaryKey(@PathVariable Long id){
		Integer  result = referralLogService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/referralLog/{id}")
	public ResponseEnvelope<Integer> updateReferralLogByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody ReferralLogVO referralLogVO){
		ReferralLogModel referralLogModel = beanMapper.map(referralLogVO, ReferralLogModel.class);
		referralLogModel.setId(id);
		Integer  result = referralLogService.updateByPrimaryKeySelective(referralLogModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
