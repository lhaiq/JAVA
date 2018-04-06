package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CommentTag;
import com.hengsu.bhyy.core.repository.CommentTagRepository;
import com.hengsu.bhyy.core.model.CommentTagModel;
import com.hengsu.bhyy.core.service.CommentTagService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentTagServiceImpl implements CommentTagService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentTagRepository commentTagRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(CommentTagModel commentTagModel) {
		return commentTagRepo.insert(beanMapper.map(commentTagModel, CommentTag.class));
	}

	@Transactional
	@Override
	public int createSelective(CommentTagModel commentTagModel) {
		commentTagModel.setCreateTime(new Date());
		return commentTagRepo.insertSelective(beanMapper.map(commentTagModel, CommentTag.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return commentTagRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CommentTagModel findByPrimaryKey(Long id) {
		CommentTag commentTag = commentTagRepo.selectByPrimaryKey(id);
		return beanMapper.map(commentTag, CommentTagModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CommentTagModel commentTagModel) {
		return commentTagRepo.selectCount(beanMapper.map(commentTagModel, CommentTag.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CommentTagModel> selectPage(CommentTagModel commentTagModel,Pageable pageable) {
		CommentTag commentTag = beanMapper.map(commentTagModel, CommentTag.class);
		return beanMapper.mapAsList(commentTagRepo.selectPage(commentTag,pageable),CommentTagModel.class);
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
		String select = "SELECT id,name,type_id AS typeId,rank,status,create_time as createTime ";

		String tables = " FROM comment_tag c ";

		StringBuffer condition = new StringBuffer();

		condition.append(" WHERE 1=1  ");

		if (StringUtils.isNotEmpty(param.get("status"))) {
			condition.append(" and c.status=" + param.get("status"));
		}

		if (StringUtils.isNotEmpty(param.get("typeId"))) {
			condition.append(" and c.type_id=" + param.get("typeId"));
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
	public int updateByPrimaryKey(CommentTagModel commentTagModel) {
		return commentTagRepo.updateByPrimaryKey(beanMapper.map(commentTagModel, CommentTag.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CommentTagModel commentTagModel) {
		return commentTagRepo.updateByPrimaryKeySelective(beanMapper.map(commentTagModel, CommentTag.class));
	}

}
