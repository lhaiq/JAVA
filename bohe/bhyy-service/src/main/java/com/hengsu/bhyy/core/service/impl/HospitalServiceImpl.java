package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Hospital;
import com.hengsu.bhyy.core.repository.HospitalRepository;
import com.hengsu.bhyy.core.model.HospitalModel;
import com.hengsu.bhyy.core.service.HospitalService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private HospitalRepository hospitalRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(HospitalModel hospitalModel) {
		return hospitalRepo.insert(beanMapper.map(hospitalModel, Hospital.class));
	}

	@Transactional
	@Override
	public int createSelective(HospitalModel hospitalModel) {
		return hospitalRepo.insertSelective(beanMapper.map(hospitalModel, Hospital.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return hospitalRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public HospitalModel findByPrimaryKey(Long id) {
		Hospital hospital = hospitalRepo.selectByPrimaryKey(id);
		return beanMapper.map(hospital, HospitalModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(HospitalModel hospitalModel) {
		return hospitalRepo.selectCount(beanMapper.map(hospitalModel, Hospital.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<HospitalModel> selectPage(HospitalModel hospitalModel,Pageable pageable) {
		Hospital hospital = beanMapper.map(hospitalModel, Hospital.class);
		return beanMapper.mapAsList(hospitalRepo.selectPage(hospital,pageable),HospitalModel.class);
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
		String select = "SELECT id,name,prov,city,area,address,tooth_chair_num as toothChairNum,\n" +
				"telephone,leader,rank,is_enable as isEnable,create_time as createTime ";

		String tables = " from hospital ";

		StringBuffer condition = new StringBuffer();

		condition.append(" WHERE 1=1 ");

		if (StringUtils.isNotEmpty(param.get("prov"))) {
			condition.append(" and prov='" + param.get("prov") + "'");
		}

		if (StringUtils.isNotEmpty(param.get("city"))) {
			condition.append(" and city=" + param.get("city") + "");
		}

		if (StringUtils.isNotEmpty(param.get("area"))) {
			condition.append(" and area=" + param.get("area"));
		}

		if (StringUtils.isNotEmpty(param.get("telephone"))) {
			condition.append(" and telephone = " + param.get("telephone"));
		}

		if (StringUtils.isNotEmpty(param.get("endDate"))) {
			condition.append(" and  establish_date < '" + param.get("endDate") + "'");
		}

		if (StringUtils.isNotEmpty(param.get("startDate"))) {
			condition.append(" and establish_date > '" + param.get("startDate") + "'");
		}


		if (StringUtils.isNotEmpty(param.get("name"))) {
			condition.append(" and name like '%" + param.get("name") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("boheJoin"))) {
			condition.append(" and bohe_join '%" + param.get("boheJoin") + "%'");
		}


		StringBuffer limitSql = new StringBuffer();
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
	public int updateByPrimaryKey(HospitalModel hospitalModel) {
		return hospitalRepo.updateByPrimaryKey(beanMapper.map(hospitalModel, Hospital.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(HospitalModel hospitalModel) {
		return hospitalRepo.updateByPrimaryKeySelective(beanMapper.map(hospitalModel, Hospital.class));
	}

}
