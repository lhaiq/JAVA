
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.NotifyMessageModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NotifyMessageService {

    int create(NotifyMessageModel notifyMessageModel);

    int createSelective(NotifyMessageModel notifyMessageModel);

    NotifyMessageModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(NotifyMessageModel notifyMessageModel);

    int updateByPrimaryKeySelective(NotifyMessageModel notifyMessageModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(NotifyMessageModel notifyMessageModel);

    List<NotifyMessageModel> selectPage(NotifyMessageModel notifyMessageModel, Pageable pageable);

    Page<Map<String, Object>> selectByDoctorId(Long doctorId, Pageable pageable);


}