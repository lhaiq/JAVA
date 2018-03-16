package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.model.VisitRecordModel;
import com.hengsu.bhyy.core.model.WeixinConfigModel;
import com.hengsu.bhyy.core.service.VisitRecordService;
import com.hengsu.bhyy.core.service.WeixinService;
import com.hengsu.bhyy.core.vo.VisitRecordVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class WeixinRestApiController {

    private final Logger logger = LoggerFactory.getLogger(WeixinRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private WeixinService weixinService;

    @GetMapping(value = "/core/weixin/config")
    public ResponseEnvelope<Map<String,Object>> weixinConfig(@RequestParam String url) {
        Map<String,Object> maps = weixinService.getWeixinConfig(url);
        ResponseEnvelope<Map<String,Object>> responseEnv = new ResponseEnvelope<>(maps, true);
        return responseEnv;
    }


}
