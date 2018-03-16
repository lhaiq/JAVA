package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Item;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("item") Item item);

    int insertSelective(@Param("item") Item item);

    Item selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("item") Item item);

    int updateByPrimaryKey(@Param("item") Item item);

    int selectCount(@Param("item") Item item);

    List<Item> selectPage(@Param("item") Item item, @Param("pageable") Pageable pageable);
}