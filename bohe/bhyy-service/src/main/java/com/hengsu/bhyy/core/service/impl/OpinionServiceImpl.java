package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Opinion;
import com.hengsu.bhyy.core.repository.OpinionRepository;
import com.hengsu.bhyy.core.model.OpinionModel;
import com.hengsu.bhyy.core.service.OpinionService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpinionServiceImpl implements OpinionService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private OpinionRepository opinionRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(OpinionModel opinionModel) {
		return opinionRepo.insert(beanMapper.map(opinionModel, Opinion.class));
	}

	@Transactional
	@Override
	public int createSelective(OpinionModel opinionModel) {
		Opinion opinion = beanMapper.map(opinionModel, Opinion.class);
		int retVal = opinionRepo.insertSelective(opinion);
		opinionModel.setId(opinion.getId());
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return opinionRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public OpinionModel findByPrimaryKey(Long id) {
		Opinion opinion = opinionRepo.selectByPrimaryKey(id);
		return beanMapper.map(opinion, OpinionModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(OpinionModel opinionModel) {
		return opinionRepo.selectCount(beanMapper.map(opinionModel, Opinion.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<OpinionModel> selectPage(OpinionModel opinionModel,Pageable pageable) {
		Opinion opinion = beanMapper.map(opinionModel, Opinion.class);
		return beanMapper.mapAsList(opinionRepo.selectPage(opinion,pageable),OpinionModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(OpinionModel opinionModel) {
		return opinionRepo.updateByPrimaryKey(beanMapper.map(opinionModel, Opinion.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(OpinionModel opinionModel) {
		return opinionRepo.updateByPrimaryKeySelective(beanMapper.map(opinionModel, Opinion.class));
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {

		String select = "SELECT\n" +
				"  id,\n" +
				"  name,\n" +
				"  phone,\n" +
				"  source,\n" +
				"  status,\n" +
				"  create_time AS createTime,\n" +
				"  content,\n" +
				"  customer_id AS customerId ";

		String tables = " FROM opinion  ";

		StringBuffer condition = new StringBuffer();

		condition.append(" WHERE 1=1 ");

		if (StringUtils.isNotEmpty(param.get("phone"))) {
			condition.append(" and phone='" + param.get("phone") + "'");
		}


		if (StringUtils.isNotEmpty(param.get("status"))) {
			condition.append(" and status=" + param.get("status"));
		}

		if (StringUtils.isNotEmpty(param.get("source"))) {
			condition.append(" and source = " + param.get("source"));
		}

		if (StringUtils.isNotEmpty(param.get("startTime"))) {
			condition.append(" and create_time < '" + param.get("startTime") + "'");
		}

		if (StringUtils.isNotEmpty(param.get("endTime"))) {
			condition.append(" and create_time > '" + param.get("endTime") + "'");
		}


		if (StringUtils.isNotEmpty(param.get("name"))) {
			condition.append(" and name like '%" + param.get("name") + "%'");
		}

		if (null != pageable.getSort()) {
			condition.append(" order by ");
			List<String> sortStr = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				sortStr.add(order.getProperty() + " " + order.getDirection());
			}
			condition.append(StringUtils.join(sortStr, ","));
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

}
