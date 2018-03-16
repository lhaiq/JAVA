package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.SysMenu;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("sysmenu") SysMenu sysmenu);

    int insertSelective(@Param("sysmenu") SysMenu sysmenu);

    SysMenu selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("sysmenu") SysMenu sysmenu);

    int updateByPrimaryKey(@Param("sysmenu") SysMenu sysmenu);

    int selectCount(@Param("sysmenu") SysMenu sysmenu);

    List<SysMenu> selectPage(@Param("sysmenu") SysMenu sysmenu, @Param("pageable") Pageable pageable);
}