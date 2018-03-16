package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Banner;
import com.hengsu.bhyy.core.repository.BannerRepository;
import com.hengsu.bhyy.core.model.BannerModel;
import com.hengsu.bhyy.core.service.BannerService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BannerRepository bannerRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(BannerModel bannerModel) {
		return bannerRepo.insert(beanMapper.map(bannerModel, Banner.class));
	}

	@Transactional
	@Override
	public int createSelective(BannerModel bannerModel) {
		bannerModel.setCreateTime(new Date());
		return bannerRepo.insertSelective(beanMapper.map(bannerModel, Banner.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return bannerRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public BannerModel findByPrimaryKey(Long id) {
		Banner banner = bannerRepo.selectByPrimaryKey(id);
		return beanMapper.map(banner, BannerModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(BannerModel bannerModel) {
		return bannerRepo.selectCount(beanMapper.map(bannerModel, Banner.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<BannerModel> selectPage(BannerModel bannerModel,Pageable pageable) {
		Banner banner = beanMapper.map(bannerModel, Banner.class);
		return beanMapper.mapAsList(bannerRepo.selectPage(banner,pageable),BannerModel.class);
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
		String select = "SELECT id,name,image,link,rank,status,create_time as createTime ";

		String tables = " FROM banner b ";

		StringBuffer condition = new StringBuffer();

		condition.append(" WHERE 1=1  ");

		if (StringUtils.isNotEmpty(param.get("status"))) {
			condition.append(" and b.status=" + param.get("status"));
		}


		if (StringUtils.isNotEmpty(param.get("startTime"))) {
			condition.append(" and b.create_time > '" + param.get("startTime")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("endTime"))) {
			condition.append(" and b.create_time < '" + param.get("endTime")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("name"))) {
			condition.append(" and b.name like '%" + param.get("name") + "%'");
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
	public int updateByPrimaryKey(BannerModel bannerModel) {
		return bannerRepo.updateByPrimaryKey(beanMapper.map(bannerModel, Banner.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(BannerModel bannerModel) {
		return bannerRepo.updateByPrimaryKeySelective(beanMapper.map(bannerModel, Banner.class));
	}

}
