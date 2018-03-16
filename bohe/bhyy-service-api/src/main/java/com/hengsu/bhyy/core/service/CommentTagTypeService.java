
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CommentTagTypeModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CommentTagTypeService {

    int create(CommentTagTypeModel commentTagTypeModel);

    int createSelective(CommentTagTypeModel commentTagTypeModel);

    CommentTagTypeModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(CommentTagTypeModel commentTagTypeModel);

    int updateByPrimaryKeySelective(CommentTagTypeModel commentTagTypeModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(CommentTagTypeModel commentTagTypeModel);

    List<CommentTagTypeModel> selectPage(CommentTagTypeModel commentTagTypeModel, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable);


}