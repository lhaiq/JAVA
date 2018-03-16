package com.hengsu.bhyy.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hengsu.bhyy.core.model.SessionUserModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableAsync
public class CacheConfig {

    @Bean
    @Qualifier("sessionCache")
    public Cache<String, SessionUserModel> sessionCache() {
        Cache<String, SessionUserModel> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.DAYS).build();
        cache.put("aaaa",new SessionUserModel(1L,SessionUserModel.ROLE_DOCTOR));
        return cache;
    }


    @Bean
    @Qualifier("validateCodeCache")
    public Cache<String, String> validateCodeCache() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MINUTES).build();
        cache.put("15208314428", "");

        return cache;
    }

}
