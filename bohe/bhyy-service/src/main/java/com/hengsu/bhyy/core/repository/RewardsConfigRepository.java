package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.RewardsConfig;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardsConfigRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("rewardsconfig") RewardsConfig rewardsconfig);

    int insertSelective(@Param("rewardsconfig") RewardsConfig rewardsconfig);

    RewardsConfig selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("rewardsconfig") RewardsConfig rewardsconfig);

    int updateByPrimaryKey(@Param("rewardsconfig") RewardsConfig rewardsconfig);

    int selectCount(@Param("rewardsconfig") RewardsConfig rewardsconfig);

    List<RewardsConfig> selectPage(@Param("rewardsconfig") RewardsConfig rewardsconfig, @Param("pageable") Pageable pageable);
}