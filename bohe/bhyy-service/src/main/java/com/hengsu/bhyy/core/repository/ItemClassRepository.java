package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.ItemClass;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemClassRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("itemclass") ItemClass itemclass);

    int insertSelective(@Param("itemclass") ItemClass itemclass);

    ItemClass selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("itemclass") ItemClass itemclass);

    int updateByPrimaryKey(@Param("itemclass") ItemClass itemclass);

    int selectCount(@Param("itemclass") ItemClass itemclass);

    List<ItemClass> selectPage(@Param("itemclass") ItemClass itemclass, @Param("pageable") Pageable pageable);
}