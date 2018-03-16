
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysRoleMenuModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SysRoleMenuService{

public int create(SysRoleMenuModel sysRoleMenuModel);

public int createSelective(SysRoleMenuModel sysRoleMenuModel);

public SysRoleMenuModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(SysRoleMenuModel sysRoleMenuModel);

public int updateByPrimaryKeySelective(SysRoleMenuModel sysRoleMenuModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(SysRoleMenuModel sysRoleMenuModel);

public List<SysRoleMenuModel> selectPage(SysRoleMenuModel sysRoleMenuModel, Pageable pageable);

}