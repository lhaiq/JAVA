package com.hengsu.bhyy.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by haiquan.li on 18/3/5.
 */
@Service
public class SmsServiceImpl implements SmsService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    @Value("${sms.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${sms.signName}")
    private String signName;

    private IAcsClient acsClient;

    @Override
    public void sendSms(String templateCode, String phoneNumber, Map<String,String> param) {

        try {
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(JSON.toJSONString(param));

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            logger.info(JSON.toJSONString(sendSmsResponse));
            if(!sendSmsResponse.getCode().equals("OK")){
                HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("60000",sendSmsResponse.getMessage()));
            }
        } catch (ClientException e) {
            logger.error("unexpected error", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            acsClient = new DefaultAcsClient(profile);
        } catch (Exception e) {
            logger.error("unexpected error", e);
        }
    }
}
