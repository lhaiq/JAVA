package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Bill;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("bill") Bill bill);

    int insertSelective(@Param("bill") Bill bill);

    Bill selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("bill") Bill bill);

    int updateByPrimaryKey(@Param("bill") Bill bill);

    int selectCount(@Param("bill") Bill bill);

    List<Bill> selectPage(@Param("bill") Bill bill, @Param("pageable") Pageable pageable);
}