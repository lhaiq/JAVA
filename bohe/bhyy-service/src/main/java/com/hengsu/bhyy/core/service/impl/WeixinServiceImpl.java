package com.hengsu.bhyy.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.hengsu.bhyy.core.model.WeixinConfigModel;
import com.hengsu.bhyy.core.service.WeixinService;
import org.eclipse.core.internal.preferences.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by haiquan.li on 18/3/2.
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    private final Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);


    @Value("${weixin.appId}")
    private String appId;

    @Value("${weixin.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={secret}";
    private static final String JS_API_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={accessToken}&type=jsapi";


    @Override
    public Map<String,Object> getWeixinConfig(String url) {
        logger.info("url:{}", url);
        WeixinConfigModel weixinConfigModel = null;
        try {
            weixinConfigModel = getWeixinConfigFromDb();
        } catch (EmptyResultDataAccessException e) {
            logger.info("no record ");
        }

        if (null == weixinConfigModel || (System.currentTimeMillis() / 1000 - weixinConfigModel.getTimestamp()) > 7200) {
            weixinConfigModel = getWeixinConfigFromWeb(url);
            saveWeixinConfig(weixinConfigModel);
        }

        String nonceStr = UUID.randomUUID().toString();
        String string1 = "jsapi_ticket=" + weixinConfigModel.getJsApiTicket() + "&noncestr=" + nonceStr
                + "&timestamp=" + weixinConfigModel.getTimestamp() + "&url=" + url;
        MessageDigest crypt = null;

        try {
            crypt = MessageDigest.getInstance("SHA-1");

            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            String signature = byteToHex(crypt.digest());
            Map<String,Object> maps = new HashMap<>();
            maps.put("appId",weixinConfigModel.getAppId());
            maps.put("timestamp",weixinConfigModel.getTimestamp());
            maps.put("nonceStr",nonceStr);
            maps.put("signature",signature);
            return maps;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private WeixinConfigModel getWeixinConfigFromDb() {
        String sql = "SELECT app_id as appId,access_token as accessToken,timestamp,js_api_ticket as jsApiTicket\n" +
                "FROM weixin_config;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WeixinConfigModel.class));
    }


    private void saveWeixinConfig(WeixinConfigModel weixinConfigModel) {
        String sql = "update  weixin_config SET timestamp=?,access_token=?,js_api_ticket=? WHERE app_id = ?";
        jdbcTemplate.update(sql, weixinConfigModel.getTimestamp(), weixinConfigModel.getAccessToken(), weixinConfigModel.getJsApiTicket(),weixinConfigModel.getAppId());
    }


    private WeixinConfigModel getWeixinConfigFromWeb(String url) {

        String tokenStr = restTemplate.getForObject(TOKEN_URL, String.class, appId, secret);
        String accessToken = JSON.parseObject(tokenStr).getString("access_token");
        String jsApiTicketStr = restTemplate.getForObject(JS_API_TICKET_URL, String.class, accessToken);
        String ticket = JSON.parseObject(jsApiTicketStr).getString("ticket");

        logger.info("accessToken:{}", accessToken);
        logger.info("jsApiTicketStr:{}", jsApiTicketStr);


        WeixinConfigModel weixinConfigModel = new WeixinConfigModel();
        weixinConfigModel.setAppId(appId);
        Long timestamp = System.currentTimeMillis() / 1000;
        weixinConfigModel.setTimestamp(timestamp);
        weixinConfigModel.setAccessToken(accessToken);
        weixinConfigModel.setJsApiTicket(ticket);
        return weixinConfigModel;
    }


    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
