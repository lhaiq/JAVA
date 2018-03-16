
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CommentTagModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CommentTagService {

    int create(CommentTagModel commentTagModel);

    int createSelective(CommentTagModel commentTagModel);

    CommentTagModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(CommentTagModel commentTagModel);

    int updateByPrimaryKeySelective(CommentTagModel commentTagModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(CommentTagModel commentTagModel);

    List<CommentTagModel> selectPage(CommentTagModel commentTagModel, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable);


}