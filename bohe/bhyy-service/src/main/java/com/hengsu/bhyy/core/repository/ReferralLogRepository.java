package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.ReferralLog;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralLogRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("referrallog") ReferralLog referrallog);

    int insertSelective(@Param("referrallog") ReferralLog referrallog);

    ReferralLog selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("referrallog") ReferralLog referrallog);

    int updateByPrimaryKey(@Param("referrallog") ReferralLog referrallog);

    int selectCount(@Param("referrallog") ReferralLog referrallog);

    List<ReferralLog> selectPage(@Param("referrallog") ReferralLog referrallog, @Param("pageable") Pageable pageable);
}