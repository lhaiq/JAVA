package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.DoctorVisitPlan;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorVisitPlanRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int deleteSelective(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan);

    int insert(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan);

    int insertSelective(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan);

    DoctorVisitPlan selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan);

    int updateByPrimaryKey(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan);

    int selectCount(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan);

    List<DoctorVisitPlan> selectPage(@Param("doctorvisitplan") DoctorVisitPlan doctorvisitplan, @Param("pageable") Pageable pageable);
}