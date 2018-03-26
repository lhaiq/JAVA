package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.ReferralRelation;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRelationRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("referralrelation") ReferralRelation referralrelation);

    int insertSelective(@Param("referralrelation") ReferralRelation referralrelation);

    ReferralRelation selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("referralrelation") ReferralRelation referralrelation);

    int updateByPrimaryKey(@Param("referralrelation") ReferralRelation referralrelation);

    int selectCount(@Param("referralrelation") ReferralRelation referralrelation);

    List<ReferralRelation> selectPage(@Param("referralrelation") ReferralRelation referralrelation, @Param("pageable") Pageable pageable);
}