package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.SysRoleMenu;
import com.hengsu.bhyy.core.repository.SysRoleMenuRepository;
import com.hengsu.bhyy.core.model.SysRoleMenuModel;
import com.hengsu.bhyy.core.service.SysRoleMenuService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysRoleMenuRepository sysRoleMenuRepo;

	@Transactional
	@Override
	public int create(SysRoleMenuModel sysRoleMenuModel) {
		return sysRoleMenuRepo.insert(beanMapper.map(sysRoleMenuModel, SysRoleMenu.class));
	}

	@Transactional
	@Override
	public int createSelective(SysRoleMenuModel sysRoleMenuModel) {
		return sysRoleMenuRepo.insertSelective(beanMapper.map(sysRoleMenuModel, SysRoleMenu.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return sysRoleMenuRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public SysRoleMenuModel findByPrimaryKey(Long id) {
		SysRoleMenu sysRoleMenu = sysRoleMenuRepo.selectByPrimaryKey(id);
		return beanMapper.map(sysRoleMenu, SysRoleMenuModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(SysRoleMenuModel sysRoleMenuModel) {
		return sysRoleMenuRepo.selectCount(beanMapper.map(sysRoleMenuModel, SysRoleMenu.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysRoleMenuModel> selectPage(SysRoleMenuModel sysRoleMenuModel,Pageable pageable) {
		SysRoleMenu sysRoleMenu = beanMapper.map(sysRoleMenuModel, SysRoleMenu.class);
		return beanMapper.mapAsList(sysRoleMenuRepo.selectPage(sysRoleMenu,pageable),SysRoleMenuModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(SysRoleMenuModel sysRoleMenuModel) {
		return sysRoleMenuRepo.updateByPrimaryKey(beanMapper.map(sysRoleMenuModel, SysRoleMenu.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(SysRoleMenuModel sysRoleMenuModel) {
		return sysRoleMenuRepo.updateByPrimaryKeySelective(beanMapper.map(sysRoleMenuModel, SysRoleMenu.class));
	}

}
