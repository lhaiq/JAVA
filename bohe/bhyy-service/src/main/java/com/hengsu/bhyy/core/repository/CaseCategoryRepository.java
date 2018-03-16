package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CaseCategory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseCategoryRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("casecategory") CaseCategory casecategory);

    int insertSelective(@Param("casecategory") CaseCategory casecategory);

    CaseCategory selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("casecategory") CaseCategory casecategory);

    int updateByPrimaryKey(@Param("casecategory") CaseCategory casecategory);

    int selectCount(@Param("casecategory") CaseCategory casecategory);

    List<CaseCategory> selectPage(@Param("casecategory") CaseCategory casecategory, @Param("pageable") Pageable pageable);
}