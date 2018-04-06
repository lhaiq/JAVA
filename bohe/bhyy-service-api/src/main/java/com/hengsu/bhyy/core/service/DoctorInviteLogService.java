
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DoctorInviteLogModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorInviteLogService {

    int create(DoctorInviteLogModel doctorInviteLogModel);

    void add(Long doctorId,String content);

    int createSelective(DoctorInviteLogModel doctorInviteLogModel);

    DoctorInviteLogModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DoctorInviteLogModel doctorInviteLogModel);

    int updateByPrimaryKeySelective(DoctorInviteLogModel doctorInviteLogModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DoctorInviteLogModel doctorInviteLogModel);

    List<DoctorInviteLogModel> selectPage(DoctorInviteLogModel doctorInviteLogModel, Pageable pageable);

}