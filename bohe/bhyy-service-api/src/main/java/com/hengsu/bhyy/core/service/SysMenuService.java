
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.SysMenuModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SysMenuService{

public int create(SysMenuModel sysMenuModel);

public int createSelective(SysMenuModel sysMenuModel);

public SysMenuModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(SysMenuModel sysMenuModel);

public int updateByPrimaryKeySelective(SysMenuModel sysMenuModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(SysMenuModel sysMenuModel);

public List<SysMenuModel> selectPage(SysMenuModel sysMenuModel, Pageable pageable);

}