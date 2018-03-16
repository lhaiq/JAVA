package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CommentTagType;
import com.hengsu.bhyy.core.repository.CommentTagTypeRepository;
import com.hengsu.bhyy.core.model.CommentTagTypeModel;
import com.hengsu.bhyy.core.service.CommentTagTypeService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentTagTypeServiceImpl implements CommentTagTypeService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentTagTypeRepository commentTagTypeRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(CommentTagTypeModel commentTagTypeModel) {
		return commentTagTypeRepo.insert(beanMapper.map(commentTagTypeModel, CommentTagType.class));
	}

	@Transactional
	@Override
	public int createSelective(CommentTagTypeModel commentTagTypeModel) {
		commentTagTypeModel.setCreateTime(new Date());
		return commentTagTypeRepo.insertSelective(beanMapper.map(commentTagTypeModel, CommentTagType.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return commentTagTypeRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CommentTagTypeModel findByPrimaryKey(Long id) {
		CommentTagType commentTagType = commentTagTypeRepo.selectByPrimaryKey(id);
		return beanMapper.map(commentTagType, CommentTagTypeModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CommentTagTypeModel commentTagTypeModel) {
		return commentTagTypeRepo.selectCount(beanMapper.map(commentTagTypeModel, CommentTagType.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CommentTagTypeModel> selectPage(CommentTagTypeModel commentTagTypeModel,Pageable pageable) {
		CommentTagType commentTagType = beanMapper.map(commentTagTypeModel, CommentTagType.class);
		return beanMapper.mapAsList(commentTagTypeRepo.selectPage(commentTagType,pageable),CommentTagTypeModel.class);
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
		String select = "SELECT id,name,rank,status,create_time as createTime ";

		String tables = " FROM comment_tag_type c ";

		StringBuffer condition = new StringBuffer();

		condition.append(" WHERE 1=1  ");

		if (StringUtils.isNotEmpty(param.get("status"))) {
			condition.append(" and c.status=" + param.get("status"));
		}


		if (StringUtils.isNotEmpty(param.get("startTime"))) {
			condition.append(" and c.create_time > '" + param.get("startTime")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("endTime"))) {
			condition.append(" and c.create_time < '" + param.get("endTime")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("name"))) {
			condition.append(" and c.name like '%" + param.get("name") + "%'");
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
	public int updateByPrimaryKey(CommentTagTypeModel commentTagTypeModel) {
		return commentTagTypeRepo.updateByPrimaryKey(beanMapper.map(commentTagTypeModel, CommentTagType.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CommentTagTypeModel commentTagTypeModel) {
		return commentTagTypeRepo.updateByPrimaryKeySelective(beanMapper.map(commentTagTypeModel, CommentTagType.class));
	}

}
