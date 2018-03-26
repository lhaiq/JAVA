
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysUserModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SysUserService {

    int create(SysUserModel sysUserModel);

    int createSelective(SysUserModel sysUserModel);

    SysUserModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(SysUserModel sysUserModel);

    int updateByPrimaryKeySelective(SysUserModel sysUserModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(SysUserModel sysUserModel);

    List<SysUserModel> selectPage(SysUserModel sysUserModel, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable);

}