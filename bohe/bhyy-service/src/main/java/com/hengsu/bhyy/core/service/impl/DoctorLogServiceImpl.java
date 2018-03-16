package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.DoctorLog;
import com.hengsu.bhyy.core.repository.DoctorLogRepository;
import com.hengsu.bhyy.core.model.DoctorLogModel;
import com.hengsu.bhyy.core.service.DoctorLogService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;

@Service
public class DoctorLogServiceImpl implements DoctorLogService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorLogRepository doctorLogRepo;

	@Transactional
	@Override
	public int create(DoctorLogModel doctorLogModel) {
		return doctorLogRepo.insert(beanMapper.map(doctorLogModel, DoctorLog.class));
	}

	@Transactional
	@Override
	public int createSelective(DoctorLogModel doctorLogModel) {
		return doctorLogRepo.insertSelective(beanMapper.map(doctorLogModel, DoctorLog.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return doctorLogRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DoctorLogModel findByPrimaryKey(Long id) {
		DoctorLog doctorLog = doctorLogRepo.selectByPrimaryKey(id);
		return beanMapper.map(doctorLog, DoctorLogModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DoctorLogModel doctorLogModel) {
		return doctorLogRepo.selectCount(beanMapper.map(doctorLogModel, DoctorLog.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DoctorLogModel> selectPage(DoctorLogModel doctorLogModel,Pageable pageable) {
		DoctorLog doctorLog = beanMapper.map(doctorLogModel, DoctorLog.class);
		return beanMapper.mapAsList(doctorLogRepo.selectPage(doctorLog,pageable),DoctorLogModel.class);
	}

	@Transactional
	@Override
	public void addDoctorLog(Long doctorId, String msg) {
		DoctorLogModel doctorLogModel = new DoctorLogModel();
		doctorLogModel.setCreateTime(new Date());
		doctorLogModel.setDoctorId(doctorId);
		doctorLogModel.setMsg(msg);
		createSelective(doctorLogModel);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DoctorLogModel doctorLogModel) {
		return doctorLogRepo.updateByPrimaryKey(beanMapper.map(doctorLogModel, DoctorLog.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DoctorLogModel doctorLogModel) {
		return doctorLogRepo.updateByPrimaryKeySelective(beanMapper.map(doctorLogModel, DoctorLog.class));
	}

}
