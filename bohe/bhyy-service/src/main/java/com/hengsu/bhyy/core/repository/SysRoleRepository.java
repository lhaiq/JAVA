package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.SysRole;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("sysrole") SysRole sysrole);

    int insertSelective(@Param("sysrole") SysRole sysrole);

    SysRole selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("sysrole") SysRole sysrole);

    int updateByPrimaryKey(@Param("sysrole") SysRole sysrole);

    int selectCount(@Param("sysrole") SysRole sysrole);

    List<SysRole> selectPage(@Param("sysrole") SysRole sysrole, @Param("pageable") Pageable pageable);
}