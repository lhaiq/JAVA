package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.SysUser;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("sysuser") SysUser sysuser);

    int insertSelective(@Param("sysuser") SysUser sysuser);

    SysUser selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("sysuser") SysUser sysuser);

    int updateByPrimaryKey(@Param("sysuser") SysUser sysuser);

    int selectCount(@Param("sysuser") SysUser sysuser);

    List<SysUser> selectPage(@Param("sysuser") SysUser sysuser, @Param("pageable") Pageable pageable);
}