package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CustomerRelation;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRelationRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("customerrelation") CustomerRelation customerrelation);

    int insertSelective(@Param("customerrelation") CustomerRelation customerrelation);

    CustomerRelation selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("customerrelation") CustomerRelation customerrelation);

    int updateByPrimaryKey(@Param("customerrelation") CustomerRelation customerrelation);

    int selectCount(@Param("customerrelation") CustomerRelation customerrelation);

    List<CustomerRelation> selectPage(@Param("customerrelation") CustomerRelation customerrelation, @Param("pageable") Pageable pageable);
}