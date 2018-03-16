package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CaseImage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseImageRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("caseimage") CaseImage caseimage);

    int insertSelective(@Param("caseimage") CaseImage caseimage);

    CaseImage selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("caseimage") CaseImage caseimage);

    int updateByPrimaryKey(@Param("caseimage") CaseImage caseimage);

    int selectCount(@Param("caseimage") CaseImage caseimage);

    List<CaseImage> selectPage(@Param("caseimage") CaseImage caseimage, @Param("pageable") Pageable pageable);
}