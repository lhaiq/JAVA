package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.DoctorLog;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorLogRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("doctorlog") DoctorLog doctorlog);

    int insertSelective(@Param("doctorlog") DoctorLog doctorlog);

    DoctorLog selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("doctorlog") DoctorLog doctorlog);

    int updateByPrimaryKey(@Param("doctorlog") DoctorLog doctorlog);

    int selectCount(@Param("doctorlog") DoctorLog doctorlog);

    List<DoctorLog> selectPage(@Param("doctorlog") DoctorLog doctorlog, @Param("pageable") Pageable pageable);
}