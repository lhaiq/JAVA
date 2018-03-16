package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.entity.SysRole;
import com.hengsu.bhyy.core.model.SysRoleMenuModel;
import com.hengsu.bhyy.core.model.SysRoleModel;
import com.hengsu.bhyy.core.model.SysUserRoleModel;
import com.hengsu.bhyy.core.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.SysUser;
import com.hengsu.bhyy.core.repository.SysUserRepository;
import com.hengsu.bhyy.core.model.SysUserModel;
import com.hengsu.bhyy.core.service.SysUserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysUserRepository sysUserRepo;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(SysUserModel sysUserModel) {
		return sysUserRepo.insert(beanMapper.map(sysUserModel, SysUser.class));
	}

	@Transactional
	@Override
	public int createSelective(SysUserModel sysUserModel) {

		sysUserModel.setCreateTime(new Date());
		SysUser sysUser = beanMapper.map(sysUserModel, SysUser.class);
		int retVal = sysUserRepo.insertSelective(sysUser);
		for(Long roleId:sysUserModel.getRoleIds()){
			SysUserRoleModel sysUserRoleModel = new SysUserRoleModel();
			sysUserRoleModel.setRoleId(roleId);
			sysUserRoleModel.setUserId(sysUser.getId());
			sysUserRoleService.createSelective(sysUserRoleModel);
		}

		sysUserModel.setId(sysUser.getId());
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		String sql= "delete from sys_user_role where user_id = ?";
		jdbcTemplate.update(sql,id);
		return sysUserRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public SysUserModel findByPrimaryKey(Long id) {

		SysUser sysUser = sysUserRepo.selectByPrimaryKey(id);
		SysUserModel sysUserModel = beanMapper.map(sysUser, SysUserModel.class);
		SysUserRoleModel param = new SysUserRoleModel();
		param.setUserId(id);
		List<SysUserRoleModel> sysUserRoleModels = sysUserRoleService.selectPage(param,new PageRequest(0,Integer.MAX_VALUE));
		List<Long> roleIds = new ArrayList<>();
		for (SysUserRoleModel sysUserRoleModel:sysUserRoleModels){
			roleIds.add(sysUserRoleModel.getRoleId());
		}

		sysUserModel.setRoleIds(roleIds);
		return sysUserModel;
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(SysUserModel sysUserModel) {
		return sysUserRepo.selectCount(beanMapper.map(sysUserModel, SysUser.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysUserModel> selectPage(SysUserModel sysUserModel,Pageable pageable) {
		SysUser sysUser = beanMapper.map(sysUserModel, SysUser.class);
		return beanMapper.mapAsList(sysUserRepo.selectPage(sysUser,pageable),SysUserModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(SysUserModel sysUserModel) {
		return sysUserRepo.updateByPrimaryKey(beanMapper.map(sysUserModel, SysUser.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(SysUserModel sysUserModel) {

		String sql= "delete from sys_user_role where user_id = ?";
		jdbcTemplate.update(sql,sysUserModel.getId());

		for(Long roleId:sysUserModel.getRoleIds()){
			SysUserRoleModel sysUserRoleModel = new SysUserRoleModel();
			sysUserRoleModel.setRoleId(roleId);
			sysUserRoleModel.setUserId(sysUserModel.getId());
			sysUserRoleService.createSelective(sysUserRoleModel);
		}
		return sysUserRepo.updateByPrimaryKeySelective(beanMapper.map(sysUserModel, SysUser.class));
	}

}
