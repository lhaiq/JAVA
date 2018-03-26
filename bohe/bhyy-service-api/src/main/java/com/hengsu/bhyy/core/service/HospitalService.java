
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.HospitalModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface HospitalService {

    int create(HospitalModel hospitalModel);

    int createSelective(HospitalModel hospitalModel);

    HospitalModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(HospitalModel hospitalModel);

    int updateByPrimaryKeySelective(HospitalModel hospitalModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(HospitalModel hospitalModel);

    List<HospitalModel> selectPage(HospitalModel hospitalModel, Pageable pageable);

    Page<Map<String,Object>> searchPage(Map<String,String> param, Pageable pageable);

    List<HospitalModel> selectByIds(List<Long> ids);


}