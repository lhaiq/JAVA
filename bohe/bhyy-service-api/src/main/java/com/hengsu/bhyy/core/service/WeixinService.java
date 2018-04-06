package com.hengsu.bhyy.core.service;


import com.hengsu.bhyy.core.model.WeixinConfigModel;

import java.util.List;
import java.util.Map;

/**
 * Created by haiquan.li on 18/3/2.
 */
public interface WeixinService {

    Map<String,Object> getWeixinConfig(String url);

    WeixinConfigModel getWeixinConfigModel();
}
