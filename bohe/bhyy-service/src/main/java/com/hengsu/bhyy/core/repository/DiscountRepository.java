package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Discount;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("discount") Discount discount);

    int insertSelective(@Param("discount") Discount discount);

    Discount selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("discount") Discount discount);

    int updateByPrimaryKey(@Param("discount") Discount discount);

    int selectCount(@Param("discount") Discount discount);

    List<Discount> selectPage(@Param("discount") Discount discount, @Param("pageable") Pageable pageable);
}