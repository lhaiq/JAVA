
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DoctorConfigModel;
import com.hengsu.bhyy.core.model.DoctorVisitPlanModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorVisitPlanService {

    int create(DoctorVisitPlanModel doctorVisitPlanModel);

    int createSelective(DoctorVisitPlanModel doctorVisitPlanModel);

    DoctorVisitPlanModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DoctorVisitPlanModel doctorVisitPlanModel);

    int updateByPrimaryKeySelective(DoctorVisitPlanModel doctorVisitPlanModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DoctorVisitPlanModel doctorVisitPlanModel);


    List<DoctorVisitPlanModel> selectPage(DoctorVisitPlanModel doctorVisitPlanModel, Pageable pageable);

    void addPlan(DoctorConfigModel configModel);

    void updatePlan(DoctorConfigModel configModel);

    void deleteSelective(DoctorVisitPlanModel doctorVisitPlanModel);

    void updateStatus(List<Long> ids,int status);

}