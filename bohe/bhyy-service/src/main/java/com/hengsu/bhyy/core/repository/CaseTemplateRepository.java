package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CaseTemplate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseTemplateRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("casetemplate") CaseTemplate casetemplate);

    int insertSelective(@Param("casetemplate") CaseTemplate casetemplate);

    CaseTemplate selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("casetemplate") CaseTemplate casetemplate);

    int updateByPrimaryKey(@Param("casetemplate") CaseTemplate casetemplate);

    int selectCount(@Param("casetemplate") CaseTemplate casetemplate);

    List<CaseTemplate> selectPage(@Param("casetemplate") CaseTemplate casetemplate, @Param("pageable") Pageable pageable);
}