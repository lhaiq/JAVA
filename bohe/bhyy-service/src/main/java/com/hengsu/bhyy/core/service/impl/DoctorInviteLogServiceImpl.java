package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.DoctorInviteLog;
import com.hengsu.bhyy.core.repository.DoctorInviteLogRepository;
import com.hengsu.bhyy.core.model.DoctorInviteLogModel;
import com.hengsu.bhyy.core.service.DoctorInviteLogService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;

@Service
public class DoctorInviteLogServiceImpl implements DoctorInviteLogService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorInviteLogRepository doctorInviteLogRepo;

	@Transactional
	@Override
	public int create(DoctorInviteLogModel doctorInviteLogModel) {
		return doctorInviteLogRepo.insert(beanMapper.map(doctorInviteLogModel, DoctorInviteLog.class));
	}

	@Transactional
	@Override
	public void add(Long doctorId, String content) {
		DoctorInviteLogModel doctorInviteLogModel = new DoctorInviteLogModel();
		doctorInviteLogModel.setContent(content);
		doctorInviteLogModel.setCreateTime(new Date());
		doctorInviteLogModel.setDoctorId(doctorId);
		createSelective(doctorInviteLogModel);
	}


	@Transactional
	@Override
	public int createSelective(DoctorInviteLogModel doctorInviteLogModel) {
		return doctorInviteLogRepo.insertSelective(beanMapper.map(doctorInviteLogModel, DoctorInviteLog.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return doctorInviteLogRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DoctorInviteLogModel findByPrimaryKey(Long id) {
		DoctorInviteLog doctorInviteLog = doctorInviteLogRepo.selectByPrimaryKey(id);
		return beanMapper.map(doctorInviteLog, DoctorInviteLogModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DoctorInviteLogModel doctorInviteLogModel) {
		return doctorInviteLogRepo.selectCount(beanMapper.map(doctorInviteLogModel, DoctorInviteLog.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DoctorInviteLogModel> selectPage(DoctorInviteLogModel doctorInviteLogModel,Pageable pageable) {
		DoctorInviteLog doctorInviteLog = beanMapper.map(doctorInviteLogModel, DoctorInviteLog.class);
		return beanMapper.mapAsList(doctorInviteLogRepo.selectPage(doctorInviteLog,pageable),DoctorInviteLogModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DoctorInviteLogModel doctorInviteLogModel) {
		return doctorInviteLogRepo.updateByPrimaryKey(beanMapper.map(doctorInviteLogModel, DoctorInviteLog.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DoctorInviteLogModel doctorInviteLogModel) {
		return doctorInviteLogRepo.updateByPrimaryKeySelective(beanMapper.map(doctorInviteLogModel, DoctorInviteLog.class));
	}

}
