
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysUserModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SysUserService{

public int create(SysUserModel sysUserModel);

public int createSelective(SysUserModel sysUserModel);

public SysUserModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(SysUserModel sysUserModel);

public int updateByPrimaryKeySelective(SysUserModel sysUserModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(SysUserModel sysUserModel);

public List<SysUserModel> selectPage(SysUserModel sysUserModel, Pageable pageable);

}