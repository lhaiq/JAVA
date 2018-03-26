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

import com.hengsu.bhyy.core.service.ReferralRelationService;
import com.hengsu.bhyy.core.model.ReferralRelationModel;
import com.hengsu.bhyy.core.vo.ReferralRelationVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class ReferralRelationRestApiController {

	private final Logger logger = LoggerFactory.getLogger(ReferralRelationRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ReferralRelationService referralRelationService;

	@GetMapping(value = "/core/referralRelation/{id}")
	public ResponseEnvelope<ReferralRelationVO> getReferralRelationById(@PathVariable Long id){
		ReferralRelationModel referralRelationModel = referralRelationService.findByPrimaryKey(id);
		ReferralRelationVO referralRelationVO =beanMapper.map(referralRelationModel, ReferralRelationVO.class);
		ResponseEnvelope<ReferralRelationVO> responseEnv = new ResponseEnvelope<>(referralRelationVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/referralRelation")
    public ResponseEnvelope<Page<ReferralRelationModel>> listReferralRelation(ReferralRelationVO referralRelationVO,Pageable pageable){

		ReferralRelationModel param = beanMapper.map(referralRelationVO, ReferralRelationModel.class);
        List<ReferralRelationModel> referralRelationModelModels = referralRelationService.selectPage(param,pageable);
        long count=referralRelationService.selectCount(param);
        Page<ReferralRelationModel> page = new PageImpl<>(referralRelationModelModels,pageable,count);
        ResponseEnvelope<Page<ReferralRelationModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/referralRelation")
	public ResponseEnvelope<Integer> createReferralRelation(@RequestBody ReferralRelationVO referralRelationVO){
		ReferralRelationModel referralRelationModel = beanMapper.map(referralRelationVO, ReferralRelationModel.class);
		Integer  result = referralRelationService.create(referralRelationModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/referralRelation/{id}")
	public ResponseEnvelope<Integer> deleteReferralRelationByPrimaryKey(@PathVariable Long id){
		Integer  result = referralRelationService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/referralRelation/{id}")
	public ResponseEnvelope<Integer> updateReferralRelationByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody ReferralRelationVO referralRelationVO){
		ReferralRelationModel referralRelationModel = beanMapper.map(referralRelationVO, ReferralRelationModel.class);
		referralRelationModel.setId(id);
		Integer  result = referralRelationService.updateByPrimaryKeySelective(referralRelationModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
