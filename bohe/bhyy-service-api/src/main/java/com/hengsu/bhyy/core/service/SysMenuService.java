
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysMenuModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysMenuService {

    int create(SysMenuModel sysMenuModel);

    int createSelective(SysMenuModel sysMenuModel);

    SysMenuModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(SysMenuModel sysMenuModel);

    int updateByPrimaryKeySelective(SysMenuModel sysMenuModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(SysMenuModel sysMenuModel);

    List<SysMenuModel> selectPage(SysMenuModel sysMenuModel, Pageable pageable);

    List<SysMenuModel> selectByRoleId(Long roleId);

}