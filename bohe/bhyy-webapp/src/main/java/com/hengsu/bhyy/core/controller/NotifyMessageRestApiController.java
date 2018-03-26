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

import com.hengsu.bhyy.core.service.NotifyMessageService;
import com.hengsu.bhyy.core.model.NotifyMessageModel;
import com.hengsu.bhyy.core.vo.NotifyMessageVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class NotifyMessageRestApiController {

    private final Logger logger = LoggerFactory.getLogger(NotifyMessageRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private NotifyMessageService notifyMessageService;


    @GetMapping(value = "/core/notifyMessage")
    public ResponseEnvelope<Page<Map<String, Object>>> listNotifyMessage(@RequestAttribute("userId") Long doctorId,
                                                                         Pageable pageable) {

        Page<Map<String, Object>> page = notifyMessageService.selectByDoctorId(doctorId, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

}
