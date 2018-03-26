
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.ReferralLogModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReferralLogService {

    int create(ReferralLogModel referralLogModel);

    int createSelective(ReferralLogModel referralLogModel);

    ReferralLogModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(ReferralLogModel referralLogModel);

    int updateByPrimaryKeySelective(ReferralLogModel referralLogModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(ReferralLogModel referralLogModel);

    List<ReferralLogModel> selectPage(ReferralLogModel referralLogModel, Pageable pageable);

}