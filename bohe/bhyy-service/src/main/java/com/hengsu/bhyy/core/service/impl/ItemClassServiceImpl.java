package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.ItemClass;
import com.hengsu.bhyy.core.repository.ItemClassRepository;
import com.hengsu.bhyy.core.model.ItemClassModel;
import com.hengsu.bhyy.core.service.ItemClassService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemClassServiceImpl implements ItemClassService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ItemClassRepository itemClassRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(ItemClassModel itemClassModel) {
		return itemClassRepo.insert(beanMapper.map(itemClassModel, ItemClass.class));
	}

	@Transactional
	@Override
	public int createSelective(ItemClassModel itemClassModel) {
		return itemClassRepo.insertSelective(beanMapper.map(itemClassModel, ItemClass.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return itemClassRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ItemClassModel findByPrimaryKey(Long id) {
		ItemClass itemClass = itemClassRepo.selectByPrimaryKey(id);
		return beanMapper.map(itemClass, ItemClassModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(ItemClassModel itemClassModel) {
		return itemClassRepo.selectCount(beanMapper.map(itemClassModel, ItemClass.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ItemClassModel> selectPage(ItemClassModel itemClassModel,Pageable pageable) {
		ItemClass itemClass = beanMapper.map(itemClassModel, ItemClass.class);
		return beanMapper.mapAsList(itemClassRepo.selectPage(itemClass,pageable),ItemClassModel.class);
	}

	@Override
	public Page<Map<String, Object>> selectByType(int type,String name, Pageable pageable) {

		String mainType = "patient_class";
		if(type==0){
			mainType="doctor_class";
		}

		String select = "SELECT ic.id,ic.class_name as className,ic.create_time as createTime,ic.type,ic.status\n" +
				"  ,ic.rank,ifnull(t.count,0) as childCount ";

		String tables = " FROM item_class ic LEFT JOIN (\n" +
				"  SELECT "+mainType+",count(*) as count\n" +
				"FROM item GROUP BY "+mainType+"\n" +
				"  ) t on ic.id=t."+mainType;

		tables+=" WHERE ic.TYPE = "+type ;

		if(StringUtils.isNotEmpty(name)){
			tables+=" and ic.class_name LIKE '%"+name+"%'";
		}

		tables+=" ORDER BY  ic.rank DESC ";


		StringBuffer limitSql = new StringBuffer();
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
		}


		List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables  + limitSql.toString());
		Long count = jdbcTemplate.queryForObject("select count(*) " + tables, Long.class);
		Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

		return page;
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(ItemClassModel itemClassModel) {
		return itemClassRepo.updateByPrimaryKey(beanMapper.map(itemClassModel, ItemClass.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(ItemClassModel itemClassModel) {
		return itemClassRepo.updateByPrimaryKeySelective(beanMapper.map(itemClassModel, ItemClass.class));
	}

}
