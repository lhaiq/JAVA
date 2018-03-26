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

import com.hengsu.bhyy.core.service.NotifyService;
import com.hengsu.bhyy.core.model.NotifyModel;
import com.hengsu.bhyy.core.vo.NotifyVO;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class NotifyRestApiController {

	private final Logger logger = LoggerFactory.getLogger(NotifyRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private NotifyService notifyService;


	@GetMapping(value = "/core/notify")
    public ResponseEnvelope<List<NotifyModel>> listNotify(@RequestAttribute("userId")Long userId){
        List<NotifyModel> notifyModelModels = notifyService.selectByCustomerId(userId);
        ResponseEnvelope<List<NotifyModel>> responseEnv = new ResponseEnvelope<>(notifyModelModels,true);
        return responseEnv;
    }


    @PutMapping(value = "/core/notify/{id}")
	public ResponseEnvelope<Integer> updateNotifyByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody NotifyVO notifyVO){
		NotifyModel notifyModel = beanMapper.map(notifyVO, NotifyModel.class);
		notifyModel.setId(id);
		notifyModel.setUpdateTime(new Date());
		Integer  result = notifyService.updateByPrimaryKeySelective(notifyModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
