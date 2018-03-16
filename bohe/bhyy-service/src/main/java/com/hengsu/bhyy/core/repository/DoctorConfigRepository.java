package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.DoctorConfig;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorConfigRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("doctorconfig") DoctorConfig doctorconfig);

    int insertSelective(@Param("doctorconfig") DoctorConfig doctorconfig);

    DoctorConfig selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("doctorconfig") DoctorConfig doctorconfig);

    int updateByPrimaryKey(@Param("doctorconfig") DoctorConfig doctorconfig);

    int selectCount(@Param("doctorconfig") DoctorConfig doctorconfig);

    List<DoctorConfig> selectPage(@Param("doctorconfig") DoctorConfig doctorconfig, @Param("pageable") Pageable pageable);
}