package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Opinion;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("opinion") Opinion opinion);

    int insertSelective(@Param("opinion") Opinion opinion);

    Opinion selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("opinion") Opinion opinion);

    int updateByPrimaryKey(@Param("opinion") Opinion opinion);

    int selectCount(@Param("opinion") Opinion opinion);

    List<Opinion> selectPage(@Param("opinion") Opinion opinion, @Param("pageable") Pageable pageable);
}