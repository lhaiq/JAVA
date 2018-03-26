package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Notify;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("notify") Notify notify);

    int insertSelective(@Param("notify") Notify notify);

    Notify selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("notify") Notify notify);

    int updateByPrimaryKey(@Param("notify") Notify notify);

    int selectCount(@Param("notify") Notify notify);

    List<Notify> selectPage(@Param("notify") Notify notify, @Param("pageable") Pageable pageable);
}