
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DoctorRecommendModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DoctorRecommendService {

    int create(DoctorRecommendModel doctorRecommendModel);

    int createSelective(DoctorRecommendModel doctorRecommendModel);

    DoctorRecommendModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DoctorRecommendModel doctorRecommendModel);

    int updateByPrimaryKeySelective(DoctorRecommendModel doctorRecommendModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DoctorRecommendModel doctorRecommendModel);

    List<DoctorRecommendModel> selectPage(DoctorRecommendModel doctorRecommendModel, Pageable pageable);

    List<Map<String, Object>> selectRecommends(Long presenter);

    void addRecommends(Long presenter,List<Long> presentees);

}