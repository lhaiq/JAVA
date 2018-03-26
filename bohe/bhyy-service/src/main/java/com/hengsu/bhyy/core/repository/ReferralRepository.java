package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Referral;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("referral") Referral referral);

    int insertSelective(@Param("referral") Referral referral);

    Referral selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("referral") Referral referral);

    int updateByPrimaryKey(@Param("referral") Referral referral);

    int selectCount(@Param("referral") Referral referral);

    List<Referral> selectPage(@Param("referral") Referral referral, @Param("pageable") Pageable pageable);
}