package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.ReferralLog;
import com.hengsu.bhyy.core.repository.ReferralLogRepository;
import com.hengsu.bhyy.core.model.ReferralLogModel;
import com.hengsu.bhyy.core.service.ReferralLogService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class ReferralLogServiceImpl implements ReferralLogService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ReferralLogRepository referralLogRepo;

	@Transactional
	@Override
	public int create(ReferralLogModel referralLogModel) {
		return referralLogRepo.insert(beanMapper.map(referralLogModel, ReferralLog.class));
	}

	@Transactional
	@Override
	public int createSelective(ReferralLogModel referralLogModel) {
		return referralLogRepo.insertSelective(beanMapper.map(referralLogModel, ReferralLog.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return referralLogRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ReferralLogModel findByPrimaryKey(Long id) {
		ReferralLog referralLog = referralLogRepo.selectByPrimaryKey(id);
		return beanMapper.map(referralLog, ReferralLogModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(ReferralLogModel referralLogModel) {
		return referralLogRepo.selectCount(beanMapper.map(referralLogModel, ReferralLog.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReferralLogModel> selectPage(ReferralLogModel referralLogModel,Pageable pageable) {
		ReferralLog referralLog = beanMapper.map(referralLogModel, ReferralLog.class);
		return beanMapper.mapAsList(referralLogRepo.selectPage(referralLog,pageable),ReferralLogModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(ReferralLogModel referralLogModel) {
		return referralLogRepo.updateByPrimaryKey(beanMapper.map(referralLogModel, ReferralLog.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(ReferralLogModel referralLogModel) {
		return referralLogRepo.updateByPrimaryKeySelective(beanMapper.map(referralLogModel, ReferralLog.class));
	}

}
