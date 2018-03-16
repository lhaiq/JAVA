package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.SysRoleMenu;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMenuRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("sysrolemenu") SysRoleMenu sysrolemenu);

    int insertSelective(@Param("sysrolemenu") SysRoleMenu sysrolemenu);

    SysRoleMenu selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("sysrolemenu") SysRoleMenu sysrolemenu);

    int updateByPrimaryKey(@Param("sysrolemenu") SysRoleMenu sysrolemenu);

    int selectCount(@Param("sysrolemenu") SysRoleMenu sysrolemenu);

    List<SysRoleMenu> selectPage(@Param("sysrolemenu") SysRoleMenu sysrolemenu, @Param("pageable") Pageable pageable);
}