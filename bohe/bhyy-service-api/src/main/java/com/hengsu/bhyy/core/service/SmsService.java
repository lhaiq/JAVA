package com.hengsu.bhyy.core.service;

import java.util.Map;

/**
 * Created by haiquan.li on 18/3/4.
 */
public interface SmsService {

    void sendSms(String templateCode,String phoneNumber,Map<String,String> param);
}
