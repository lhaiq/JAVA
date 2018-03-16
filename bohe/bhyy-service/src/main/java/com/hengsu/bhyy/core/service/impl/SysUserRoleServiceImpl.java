package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.SysUserRole;
import com.hengsu.bhyy.core.repository.SysUserRoleRepository;
import com.hengsu.bhyy.core.model.SysUserRoleModel;
import com.hengsu.bhyy.core.service.SysUserRoleService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysUserRoleRepository sysUserRoleRepo;

	@Transactional
	@Override
	public int create(SysUserRoleModel sysUserRoleModel) {
		return sysUserRoleRepo.insert(beanMapper.map(sysUserRoleModel, SysUserRole.class));
	}

	@Transactional
	@Override
	public int createSelective(SysUserRoleModel sysUserRoleModel) {
		return sysUserRoleRepo.insertSelective(beanMapper.map(sysUserRoleModel, SysUserRole.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return sysUserRoleRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public SysUserRoleModel findByPrimaryKey(Long id) {
		SysUserRole sysUserRole = sysUserRoleRepo.selectByPrimaryKey(id);
		return beanMapper.map(sysUserRole, SysUserRoleModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(SysUserRoleModel sysUserRoleModel) {
		return sysUserRoleRepo.selectCount(beanMapper.map(sysUserRoleModel, SysUserRole.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysUserRoleModel> selectPage(SysUserRoleModel sysUserRoleModel,Pageable pageable) {
		SysUserRole sysUserRole = beanMapper.map(sysUserRoleModel, SysUserRole.class);
		return beanMapper.mapAsList(sysUserRoleRepo.selectPage(sysUserRole,pageable),SysUserRoleModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(SysUserRoleModel sysUserRoleModel) {
		return sysUserRoleRepo.updateByPrimaryKey(beanMapper.map(sysUserRoleModel, SysUserRole.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(SysUserRoleModel sysUserRoleModel) {
		return sysUserRoleRepo.updateByPrimaryKeySelective(beanMapper.map(sysUserRoleModel, SysUserRole.class));
	}

}
