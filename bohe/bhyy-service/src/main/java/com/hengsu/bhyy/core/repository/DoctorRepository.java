package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Doctor;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository {
    int deleteByPrimaryKey(@Param("doctorId") Long doctorId);

    int insert(@Param("doctor") Doctor doctor);

    int insertSelective(@Param("doctor") Doctor doctor);

    Doctor selectByPrimaryKey(@Param("doctorId") Long doctorId);

    int updateByPrimaryKeySelective(@Param("doctor") Doctor doctor);

    int updateByPrimaryKey(@Param("doctor") Doctor doctor);

    int selectCount(@Param("doctor") Doctor doctor);

    List<Doctor> selectPage(@Param("doctor") Doctor doctor, @Param("pageable") Pageable pageable);
}