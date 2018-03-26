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

import com.hengsu.bhyy.core.service.RewardsConfigService;
import com.hengsu.bhyy.core.model.RewardsConfigModel;
import com.hengsu.bhyy.core.vo.RewardsConfigVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class RewardsConfigRestApiController {

	private final Logger logger = LoggerFactory.getLogger(RewardsConfigRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private RewardsConfigService rewardsConfigService;


	@GetMapping(value = "/core/rewardsConfig")
    public ResponseEnvelope<List<RewardsConfigModel>> listRewardsConfig(RewardsConfigVO rewardsConfigVO,Pageable pageable){
		RewardsConfigModel param = beanMapper.map(rewardsConfigVO, RewardsConfigModel.class);
        List<RewardsConfigModel> rewardsConfigModelModels = rewardsConfigService.selectPage(param,pageable);
        ResponseEnvelope<List<RewardsConfigModel>> responseEnv = new ResponseEnvelope<>(rewardsConfigModelModels,true);
        return responseEnv;
    }


    @PutMapping(value = "/core/rewardsConfig")
	public ResponseEnvelope<Integer> updateRewardsConfigByPrimaryKeySelective(
					@RequestBody List<RewardsConfigVO> rewardsConfigVOs){
		List<RewardsConfigModel> rewardsConfigModels = beanMapper.mapAsList(rewardsConfigVOs,RewardsConfigModel.class);
		for(RewardsConfigModel rewardsConfigModel:rewardsConfigModels){
			rewardsConfigService.updateByPrimaryKeySelective(rewardsConfigModel);
		}
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1,true);
        return responseEnv;
	}

}
