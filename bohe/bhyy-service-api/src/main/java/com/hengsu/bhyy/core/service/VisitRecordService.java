
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.VisitRecordModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface VisitRecordService {

    int create(VisitRecordModel visitRecordModel);

    int createSelective(VisitRecordModel visitRecordModel);

    VisitRecordModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(VisitRecordModel visitRecordModel);

    int updateByPrimaryKeySelective(VisitRecordModel visitRecordModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(VisitRecordModel visitRecordModel);

    List<VisitRecordModel> selectPage(VisitRecordModel visitRecordModel, Pageable pageable);

    List<Map<String,Object>> selectByAppointId(Long appointId);

}