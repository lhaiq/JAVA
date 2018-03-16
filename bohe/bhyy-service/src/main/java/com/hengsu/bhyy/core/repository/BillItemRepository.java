package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.BillItem;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("billitem") BillItem billitem);

    int insertSelective(@Param("billitem") BillItem billitem);

    BillItem selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("billitem") BillItem billitem);

    int updateByPrimaryKey(@Param("billitem") BillItem billitem);

    int selectCount(@Param("billitem") BillItem billitem);

    List<BillItem> selectPage(@Param("billitem") BillItem billitem, @Param("pageable") Pageable pageable);
}