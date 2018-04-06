
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysRoleModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysRoleService {

    int create(SysRoleModel sysRoleModel);

    int createSelective(SysRoleModel sysRoleModel);

    SysRoleModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(SysRoleModel sysRoleModel);

    int updateByPrimaryKeySelective(SysRoleModel sysRoleModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(SysRoleModel sysRoleModel);

    List<SysRoleModel> selectPage(SysRoleModel sysRoleModel, Pageable pageable);

    SysRoleModel findByUserId(Long userId);

}