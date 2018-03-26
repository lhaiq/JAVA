
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DoctorModel;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DoctorService {

    int create(DoctorModel doctorModel);

    int createSelective(DoctorModel doctorModel);

    DoctorModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DoctorModel doctorModel);

    int updateByPrimaryKeySelective(DoctorModel doctorModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DoctorModel doctorModel);

    List<DoctorModel> selectPage(DoctorModel doctorModel, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, Object> param, Pageable pageable);

    Page<Map<String, Object>> searchPageForApp(List<Long> dayOfWeek,String name,String itemName, Pageable pageable);


    void addBalance(Long id, Long billId,BigDecimal money);

}