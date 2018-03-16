
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.AppointmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    int create(AppointmentModel appointmentModel);

    int createSelective(AppointmentModel appointmentModel);

    AppointmentModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(AppointmentModel appointmentModel);

    int updateByPrimaryKeySelective(AppointmentModel appointmentModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(AppointmentModel appointmentModel);

    List<AppointmentModel> selectPage(AppointmentModel appointmentModel, Pageable pageable);

    Page<Map<String,Object>> searchAppointPage(Map<String,String> param, Pageable pageable);

    Page<Map<String,Object>> searchVisitPage(Map<String,String> param, Pageable pageable);

    Page<Map<String,Object>> selectDoctorPage(Long doctorId,int status, Pageable pageable);

    Page<Map<String,Object>> selectCustomerPage(Long customerId,int status, Pageable pageable);


}