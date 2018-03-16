package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CaseReport;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseReportRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("casereport") CaseReport casereport);

    int insertSelective(@Param("casereport") CaseReport casereport);

    CaseReport selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("casereport") CaseReport casereport);

    int updateByPrimaryKeyWithBLOBs(@Param("casereport") CaseReport casereport);

    int updateByPrimaryKey(@Param("casereport") CaseReport casereport);

    int selectCount(@Param("casereport") CaseReport casereport);

    List<CaseReport> selectPage(@Param("casereport") CaseReport casereport, @Param("pageable") Pageable pageable);
}