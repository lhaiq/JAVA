package com.hengsu.bhyy.core.model;


/**
 * Created by haiquan.li on 18/3/2.
 */
public class WeixinConfigModel {


    private String appId;
    private String accessToken;
    private String jsApiTicket;
    private Long timestamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
