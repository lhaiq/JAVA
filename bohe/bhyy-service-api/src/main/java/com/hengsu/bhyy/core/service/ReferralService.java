
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.ReferralModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ReferralService {

    int create(ReferralModel referralModel);

    int createSelective(ReferralModel referralModel);

    ReferralModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(ReferralModel referralModel);

    int updateByPrimaryKeySelective(ReferralModel referralModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(ReferralModel referralModel);

    List<ReferralModel> selectPage(ReferralModel referralModel, Pageable pageable);

    void scan(ReferralModel referralModel);

    void consult(Long id);

    void sendReport(List<Long> ids);

    Page<Map<String, Object>> searchAppointPage(Map<String, String> param, Pageable pageable);


}