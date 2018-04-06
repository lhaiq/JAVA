package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.DoctorInviteLog;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorInviteLogRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("doctorinvitelog") DoctorInviteLog doctorinvitelog);

    int insertSelective(@Param("doctorinvitelog") DoctorInviteLog doctorinvitelog);

    DoctorInviteLog selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("doctorinvitelog") DoctorInviteLog doctorinvitelog);

    int updateByPrimaryKey(@Param("doctorinvitelog") DoctorInviteLog doctorinvitelog);

    int selectCount(@Param("doctorinvitelog") DoctorInviteLog doctorinvitelog);

    List<DoctorInviteLog> selectPage(@Param("doctorinvitelog") DoctorInviteLog doctorinvitelog, @Param("pageable") Pageable pageable);
}