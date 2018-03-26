
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.ReferralRelationModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReferralRelationService {

    int create(ReferralRelationModel referralRelationModel);

    int createSelective(ReferralRelationModel referralRelationModel);

    ReferralRelationModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(ReferralRelationModel referralRelationModel);

    int updateByPrimaryKeySelective(ReferralRelationModel referralRelationModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(ReferralRelationModel referralRelationModel);

    List<ReferralRelationModel> selectPage(ReferralRelationModel referralRelationModel, Pageable pageable);

}