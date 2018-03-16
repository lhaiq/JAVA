package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.OpinionRecord;
import com.hengsu.bhyy.core.repository.OpinionRecordRepository;
import com.hengsu.bhyy.core.model.OpinionRecordModel;
import com.hengsu.bhyy.core.service.OpinionRecordService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;
import java.util.Map;

@Service
public class OpinionRecordServiceImpl implements OpinionRecordService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private OpinionRecordRepository opinionRecordRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(OpinionRecordModel opinionRecordModel) {
		return opinionRecordRepo.insert(beanMapper.map(opinionRecordModel, OpinionRecord.class));
	}

	@Transactional
	@Override
	public int createSelective(OpinionRecordModel opinionRecordModel) {
		return opinionRecordRepo.insertSelective(beanMapper.map(opinionRecordModel, OpinionRecord.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return opinionRecordRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public OpinionRecordModel findByPrimaryKey(Long id) {
		OpinionRecord opinionRecord = opinionRecordRepo.selectByPrimaryKey(id);
		return beanMapper.map(opinionRecord, OpinionRecordModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(OpinionRecordModel opinionRecordModel) {
		return opinionRecordRepo.selectCount(beanMapper.map(opinionRecordModel, OpinionRecord.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<OpinionRecordModel> selectPage(OpinionRecordModel opinionRecordModel,Pageable pageable) {
		OpinionRecord opinionRecord = beanMapper.map(opinionRecordModel, OpinionRecord.class);
		return beanMapper.mapAsList(opinionRecordRepo.selectPage(opinionRecord,pageable),OpinionRecordModel.class);
	}

	@Override
	public List<Map<String, Object>> selectByOpinionId(Long opinionId) {
		String sql="SELECT\n" +
				"  ore.id,\n" +
				"  ore.opinion_id  AS opinionId,\n" +
				"  ore.create_time AS createTime,\n" +
				"  ore.manager_id  AS managerId,\n" +
				"  ore.type,\n" +
				"  ore.content,\n" +
				"  su.real_name    AS managerName,\n" +
				"  su.phone           managerPhone\n" +
				"FROM opinion_record ore, sys_user su\n" +
				"WHERE ore.manager_id = su.id AND ore.opinion_id = ?;";
		return jdbcTemplate.queryForList(sql,opinionId);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(OpinionRecordModel opinionRecordModel) {
		return opinionRecordRepo.updateByPrimaryKey(beanMapper.map(opinionRecordModel, OpinionRecord.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(OpinionRecordModel opinionRecordModel) {
		return opinionRecordRepo.updateByPrimaryKeySelective(beanMapper.map(opinionRecordModel, OpinionRecord.class));
	}

}
