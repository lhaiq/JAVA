
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DoctorLogModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorLogService {

    int create(DoctorLogModel doctorLogModel);

    int createSelective(DoctorLogModel doctorLogModel);

    DoctorLogModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DoctorLogModel doctorLogModel);

    int updateByPrimaryKeySelective(DoctorLogModel doctorLogModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DoctorLogModel doctorLogModel);

    List<DoctorLogModel> selectPage(DoctorLogModel doctorLogModel, Pageable pageable);

    void addDoctorLog(Long doctorId,String msg);

}