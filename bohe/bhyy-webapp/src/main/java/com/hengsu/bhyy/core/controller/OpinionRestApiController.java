package com.hengsu.bhyy.core.controller;

import com.google.common.cache.Cache;
import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.model.SessionUserModel;
import com.hengsu.bhyy.core.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.hengsu.bhyy.core.service.OpinionService;
import com.hengsu.bhyy.core.model.OpinionModel;
import com.hengsu.bhyy.core.vo.OpinionVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class OpinionRestApiController {

	private final Logger logger = LoggerFactory.getLogger(OpinionRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private OpinionService opinionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	@Qualifier("sessionCache")
	private Cache<String, SessionUserModel> sessionCache;

	@GetMapping(value = "/core/opinion/{id}")
	public ResponseEnvelope<OpinionVO> getOpinionById(@PathVariable Long id){
		OpinionModel opinionModel = opinionService.findByPrimaryKey(id);
		OpinionVO opinionVO =beanMapper.map(opinionModel, OpinionVO.class);
		ResponseEnvelope<OpinionVO> responseEnv = new ResponseEnvelope<>(opinionVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/opinion")
    public ResponseEnvelope<Page<OpinionModel>> listOpinion(OpinionVO opinionVO,Pageable pageable){

		OpinionModel param = beanMapper.map(opinionVO, OpinionModel.class);
        List<OpinionModel> opinionModelModels = opinionService.selectPage(param,pageable);
        long count=opinionService.selectCount(param);
        Page<OpinionModel> page = new PageImpl<>(opinionModelModels,pageable,count);
        ResponseEnvelope<Page<OpinionModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/opinion")
	public ResponseEnvelope<Long> createOpinion(@RequestHeader("Authorization") String authToken,
												   @RequestBody OpinionVO opinionVO){
		SessionUserModel sessionUserModel = sessionCache.getIfPresent(authToken);
		OpinionModel opinionModel = beanMapper.map(opinionVO, OpinionModel.class);

		CustomerModel customerModel = customerService.findByPrimaryKey(sessionUserModel.getUserId());
		opinionModel.setCustomerId(sessionUserModel.getUserId());
		opinionModel.setSource(sessionUserModel.getRole());
		opinionModel.setCreateTime(new Date());
		opinionModel.setName(customerModel.getRealName());
		opinionModel.setPhone(customerModel.getPhone());
		opinionService.createSelective(opinionModel);
		ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(opinionModel.getId(),true);
        return responseEnv;
	}

	@PostMapping(value = "/core/opinion/search")
	public ResponseEnvelope<Page<Map<String,Object>>> searchPage(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String,Object>> page = opinionService.searchPage(param,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

    @DeleteMapping(value = "/core/opinion/{id}")
	public ResponseEnvelope<Integer> deleteOpinionByPrimaryKey(@PathVariable Long id){
		Integer  result = opinionService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/opinion/{id}")
	public ResponseEnvelope<Integer> updateOpinionByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody OpinionVO opinionVO){
		OpinionModel opinionModel = beanMapper.map(opinionVO, OpinionModel.class);
		opinionModel.setId(id);
		Integer  result = opinionService.updateByPrimaryKeySelective(opinionModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
