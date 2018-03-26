
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.RewardsConfigModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RewardsConfigService {

     String REFERRAL_PATIENT="referral_patient";
     String INVITE_DOCTOR="invite_doctor";

    int create(RewardsConfigModel rewardsConfigModel);

    int createSelective(RewardsConfigModel rewardsConfigModel);

    RewardsConfigModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(RewardsConfigModel rewardsConfigModel);

    int updateByPrimaryKeySelective(RewardsConfigModel rewardsConfigModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(RewardsConfigModel rewardsConfigModel);

    List<RewardsConfigModel> selectPage(RewardsConfigModel rewardsConfigModel, Pageable pageable);

}