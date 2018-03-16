package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.SysUserRole;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("sysuserrole") SysUserRole sysuserrole);

    int insertSelective(@Param("sysuserrole") SysUserRole sysuserrole);

    SysUserRole selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("sysuserrole") SysUserRole sysuserrole);

    int updateByPrimaryKey(@Param("sysuserrole") SysUserRole sysuserrole);

    int selectCount(@Param("sysuserrole") SysUserRole sysuserrole);

    List<SysUserRole> selectPage(@Param("sysuserrole") SysUserRole sysuserrole, @Param("pageable") Pageable pageable);
}