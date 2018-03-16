
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.OpinionRecordModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OpinionRecordService {

    int create(OpinionRecordModel opinionRecordModel);

    int createSelective(OpinionRecordModel opinionRecordModel);

    OpinionRecordModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(OpinionRecordModel opinionRecordModel);

    int updateByPrimaryKeySelective(OpinionRecordModel opinionRecordModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(OpinionRecordModel opinionRecordModel);

    List<OpinionRecordModel> selectPage(OpinionRecordModel opinionRecordModel, Pageable pageable);

    List<Map<String,Object>> selectByOpinionId(Long opinionId);


}