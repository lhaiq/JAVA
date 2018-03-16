package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Appointment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("appointment") Appointment appointment);

    int insertSelective(@Param("appointment") Appointment appointment);

    Appointment selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("appointment") Appointment appointment);

    int updateByPrimaryKey(@Param("appointment") Appointment appointment);

    int selectCount(@Param("appointment") Appointment appointment);

    List<Appointment> selectPage(@Param("appointment") Appointment appointment, @Param("pageable") Pageable pageable);
}