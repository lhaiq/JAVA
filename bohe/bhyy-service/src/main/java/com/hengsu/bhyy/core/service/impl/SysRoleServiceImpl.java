package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.entity.SysRole;
import com.hengsu.bhyy.core.model.SysRoleMenuModel;
import com.hengsu.bhyy.core.model.SysRoleModel;
import com.hengsu.bhyy.core.repository.SysRoleRepository;
import com.hengsu.bhyy.core.service.SysRoleMenuService;
import com.hengsu.bhyy.core.service.SysRoleService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private SysRoleRepository sysRoleRepo;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    @Override
    public int create(SysRoleModel sysRoleModel) {
        return sysRoleRepo.insert(beanMapper.map(sysRoleModel, SysRole.class));
    }

    @Transactional
    @Override
    public int createSelective(SysRoleModel sysRoleModel) {
        sysRoleModel.setCreateTime(new Date());
        SysRole sysRole = beanMapper.map(sysRoleModel, SysRole.class);
        int retVal = sysRoleRepo.insertSelective(sysRole);
        for (Long menuId : sysRoleModel.getMenuIds()) {
            SysRoleMenuModel sysRoleMenuModel = new SysRoleMenuModel();
            sysRoleMenuModel.setMenuId(menuId);
            sysRoleMenuModel.setRoleId(sysRole.getId());
            sysRoleMenuService.createSelective(sysRoleMenuModel);
        }

        sysRoleModel.setId(sysRole.getId());
        return retVal;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        String sql = "delete from sys_role_menu where role_id = ?";
        jdbcTemplate.update(sql, id);
        return sysRoleRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public SysRoleModel findByPrimaryKey(Long id) {
        SysRole sysRole = sysRoleRepo.selectByPrimaryKey(id);
        SysRoleModel sysRoleModel = beanMapper.map(sysRole, SysRoleModel.class);
        SysRoleMenuModel param = new SysRoleMenuModel();
        param.setRoleId(id);
        List<SysRoleMenuModel> sysRoleMenuModels = sysRoleMenuService.selectPage(param, new PageRequest(0, Integer.MAX_VALUE));
        List<Long> menuIds = new ArrayList<>();
        for (SysRoleMenuModel sysRoleMenuModel : sysRoleMenuModels) {
            menuIds.add(sysRoleMenuModel.getMenuId());
        }

        sysRoleModel.setMenuIds(menuIds);
        return sysRoleModel;
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(SysRoleModel sysRoleModel) {
        return sysRoleRepo.selectCount(beanMapper.map(sysRoleModel, SysRole.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SysRoleModel> selectPage(SysRoleModel sysRoleModel, Pageable pageable) {
        SysRole sysRole = beanMapper.map(sysRoleModel, SysRole.class);
        return beanMapper.mapAsList(sysRoleRepo.selectPage(sysRole, pageable), SysRoleModel.class);
    }

    @Override
    public SysRoleModel findByUserId(Long userId) {
        String sql = "SELECT sr.id,sr.name,sr.create_time as createTime\n" +
                "FROM sys_user_role sur,sys_role sr WHERE sur.role_id=sr.id and sur.user_id=?";
        List<SysRoleModel> sysRoleModels = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SysRoleModel.class), userId);
        if (CollectionUtils.isNotEmpty(sysRoleModels)) {
            return sysRoleModels.get(0);
        }
        return null;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(SysRoleModel sysRoleModel) {
        return sysRoleRepo.updateByPrimaryKey(beanMapper.map(sysRoleModel, SysRole.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(SysRoleModel sysRoleModel) {
        String sql = "delete from sys_role_menu where role_id = ?";
        jdbcTemplate.update(sql, sysRoleModel.getId());
        for (Long menuId : sysRoleModel.getMenuIds()) {
            SysRoleMenuModel sysRoleMenuModel = new SysRoleMenuModel();
            sysRoleMenuModel.setMenuId(menuId);
            sysRoleMenuModel.setRoleId(sysRoleModel.getId());
            sysRoleMenuService.createSelective(sysRoleMenuModel);
        }
        return sysRoleRepo.updateByPrimaryKeySelective(beanMapper.map(sysRoleModel, SysRole.class));
    }

}
