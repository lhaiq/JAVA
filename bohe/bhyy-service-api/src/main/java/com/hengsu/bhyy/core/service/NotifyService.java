
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.NotifyModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotifyService {


    //患者扫码通知
    public static final int TYPE_0=0;
    //患者登陆通知
    public static final int TYPE_1=1;
    //患者预约通知
    public static final int TYPE_2=2;
    //患者取消预约通知
    public static final int TYPE_3=3;
    //患者支付通知
    public static final int TYPE_4=4;
    //医生注册通知
    public static final int TYPE_5=5;
    //医生认证通知
    public static final int TYPE_6=6;

    int create(NotifyModel notifyModel);

    int createSelective(NotifyModel notifyModel);

    NotifyModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(NotifyModel notifyModel);

    int updateByPrimaryKeySelective(NotifyModel notifyModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(NotifyModel notifyModel);

    List<NotifyModel> selectPage(NotifyModel notifyModel, Pageable pageable);

    List<NotifyModel> selectByCustomerId(Long customerId);



}