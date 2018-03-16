
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.OpinionModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OpinionService {

    int create(OpinionModel opinionModel);

    int createSelective(OpinionModel opinionModel);

    OpinionModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(OpinionModel opinionModel);

    int updateByPrimaryKeySelective(OpinionModel opinionModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(OpinionModel opinionModel);

    List<OpinionModel> selectPage(OpinionModel opinionModel, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable);


}