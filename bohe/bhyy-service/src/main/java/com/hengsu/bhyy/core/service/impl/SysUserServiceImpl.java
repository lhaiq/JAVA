package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.entity.SysUser;
import com.hengsu.bhyy.core.model.SysUserModel;
import com.hengsu.bhyy.core.model.SysUserRoleModel;
import com.hengsu.bhyy.core.repository.SysUserRepository;
import com.hengsu.bhyy.core.service.SysUserRoleService;
import com.hengsu.bhyy.core.service.SysUserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        SysUserRoleModel sysUserRoleModel = new SysUserRoleModel();
        sysUserRoleModel.setRoleId(sysUserModel.getRoleId());
        sysUserRoleModel.setUserId(sysUser.getId());
        sysUserRoleService.createSelective(sysUserRoleModel);

        sysUserModel.setId(sysUser.getId());
        return retVal;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        String sql = "delete from sys_user_role where user_id = ?";
        jdbcTemplate.update(sql, id);
        return sysUserRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public SysUserModel findByPrimaryKey(Long id) {

        SysUser sysUser = sysUserRepo.selectByPrimaryKey(id);
        SysUserModel sysUserModel = beanMapper.map(sysUser, SysUserModel.class);
        SysUserRoleModel param = new SysUserRoleModel();
        param.setUserId(id);
        List<SysUserRoleModel> sysUserRoleModels = sysUserRoleService.selectPage(param, new PageRequest(0, Integer.MAX_VALUE));
        if (CollectionUtils.isNotEmpty(sysUserRoleModels)) {
            sysUserModel.setRoleId(sysUserRoleModels.get(0).getRoleId());
        }


        return sysUserModel;
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(SysUserModel sysUserModel) {
        return sysUserRepo.selectCount(beanMapper.map(sysUserModel, SysUser.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SysUserModel> selectPage(SysUserModel sysUserModel, Pageable pageable) {
        SysUser sysUser = beanMapper.map(sysUserModel, SysUser.class);
        return beanMapper.mapAsList(sysUserRepo.selectPage(sysUser, pageable), SysUserModel.class);
    }

    @Override
    public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
        String select = "SELECT su.id,su.real_name as realName,su.phone,su.create_time as createTime,sr.id as roleId,sr.name as roleName ";

        String tables = " FROM sys_user su,sys_user_role sur,sys_role  sr ";

        StringBuffer condition = new StringBuffer();

        condition.append(" WHERE su.id=sur.user_id and sur.role_id=sr.id ");

        if (StringUtils.isNotEmpty(param.get("realName"))) {
            condition.append(" and su.real_name like '%" + param.get("realName") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("phone"))) {
            condition.append(" and su.phone like '%" + param.get("phone") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("roleId"))) {
            condition.append(" and sr.id = " + param.get("roleId"));
        }

        if (StringUtils.isNotEmpty(param.get("startDate"))) {
            condition.append(" and su.create_time >= '" + param.get("startDate") + "'");
        }

        if (StringUtils.isNotEmpty(param.get("endDate"))) {
            condition.append(" and su.create_time <= '" + param.get("endDate") + "'");
        }


        StringBuffer limitSql = new StringBuffer();
        limitSql.append(" order by su.id desc ");
        if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
            limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
        }


        List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
        Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);

        Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

        return page;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(SysUserModel sysUserModel) {
        return sysUserRepo.updateByPrimaryKey(beanMapper.map(sysUserModel, SysUser.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(SysUserModel sysUserModel) {

        String sql = "delete from sys_user_role where user_id = ?";
        jdbcTemplate.update(sql, sysUserModel.getId());

        SysUserRoleModel sysUserRoleModel = new SysUserRoleModel();
        sysUserRoleModel.setRoleId(sysUserModel.getRoleId());
        sysUserRoleModel.setUserId(sysUserModel.getId());
        sysUserRoleService.createSelective(sysUserRoleModel);
        return sysUserRepo.updateByPrimaryKeySelective(beanMapper.map(sysUserModel, SysUser.class));
    }

}
