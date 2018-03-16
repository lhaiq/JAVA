package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Banner;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("banner") Banner banner);

    int insertSelective(@Param("banner") Banner banner);

    Banner selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("banner") Banner banner);

    int updateByPrimaryKey(@Param("banner") Banner banner);

    int selectCount(@Param("banner") Banner banner);

    List<Banner> selectPage(@Param("banner") Banner banner, @Param("pageable") Pageable pageable);
}