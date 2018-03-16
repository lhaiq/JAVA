
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysUserRoleModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SysUserRoleService{

public int create(SysUserRoleModel sysUserRoleModel);

public int createSelective(SysUserRoleModel sysUserRoleModel);

public SysUserRoleModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(SysUserRoleModel sysUserRoleModel);

public int updateByPrimaryKeySelective(SysUserRoleModel sysUserRoleModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(SysUserRoleModel sysUserRoleModel);

public List<SysUserRoleModel> selectPage(SysUserRoleModel sysUserRoleModel, Pageable pageable);

}