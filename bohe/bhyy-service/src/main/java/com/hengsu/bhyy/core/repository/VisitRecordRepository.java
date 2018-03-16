package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.VisitRecord;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRecordRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("visitrecord") VisitRecord visitrecord);

    int insertSelective(@Param("visitrecord") VisitRecord visitrecord);

    VisitRecord selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("visitrecord") VisitRecord visitrecord);

    int updateByPrimaryKey(@Param("visitrecord") VisitRecord visitrecord);

    int selectCount(@Param("visitrecord") VisitRecord visitrecord);

    List<VisitRecord> selectPage(@Param("visitrecord") VisitRecord visitrecord, @Param("pageable") Pageable pageable);
}