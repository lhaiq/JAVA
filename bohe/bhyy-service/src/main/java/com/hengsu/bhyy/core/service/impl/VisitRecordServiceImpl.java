package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.VisitRecord;
import com.hengsu.bhyy.core.repository.VisitRecordRepository;
import com.hengsu.bhyy.core.model.VisitRecordModel;
import com.hengsu.bhyy.core.service.VisitRecordService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VisitRecordServiceImpl implements VisitRecordService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private VisitRecordRepository visitRecordRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(VisitRecordModel visitRecordModel) {
		return visitRecordRepo.insert(beanMapper.map(visitRecordModel, VisitRecord.class));
	}

	@Transactional
	@Override
	public int createSelective(VisitRecordModel visitRecordModel) {
		visitRecordModel.setCreateTime(new Date());
		return visitRecordRepo.insertSelective(beanMapper.map(visitRecordModel, VisitRecord.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return visitRecordRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public VisitRecordModel findByPrimaryKey(Long id) {
		VisitRecord visitRecord = visitRecordRepo.selectByPrimaryKey(id);
		return beanMapper.map(visitRecord, VisitRecordModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(VisitRecordModel visitRecordModel) {
		return visitRecordRepo.selectCount(beanMapper.map(visitRecordModel, VisitRecord.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<VisitRecordModel> selectPage(VisitRecordModel visitRecordModel,Pageable pageable) {
		VisitRecord visitRecord = beanMapper.map(visitRecordModel, VisitRecord.class);
		return beanMapper.mapAsList(visitRecordRepo.selectPage(visitRecord,pageable),VisitRecordModel.class);
	}

	@Override
	public List<Map<String, Object>> selectByAppointId(Long appointId) {
		String sql="SELECT\n" +
				"  vr.id,\n" +
				"  vr.customer_id AS customerId,\n" +
				"  vr.content,\n" +
				"  vr.type,\n" +
				"  vr.create_time as createTime,\n" +
				"  vr.manager_id     AS managerId,\n" +
				"\n" +
				"  su.real_name   AS managerName\n" +
				"FROM visit_record vr, sys_user su\n" +
				"WHERE vr.manager_id = su.id AND vr.appoint_id = ?\n" +
				"ORDER BY vr.create_time DESC";
		return jdbcTemplate.queryForList(sql,appointId);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(VisitRecordModel visitRecordModel) {
		return visitRecordRepo.updateByPrimaryKey(beanMapper.map(visitRecordModel, VisitRecord.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(VisitRecordModel visitRecordModel) {
		return visitRecordRepo.updateByPrimaryKeySelective(beanMapper.map(visitRecordModel, VisitRecord.class));
	}

}
