package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.RewardsConfig;
import com.hengsu.bhyy.core.repository.RewardsConfigRepository;
import com.hengsu.bhyy.core.model.RewardsConfigModel;
import com.hengsu.bhyy.core.service.RewardsConfigService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class RewardsConfigServiceImpl implements RewardsConfigService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private RewardsConfigRepository rewardsConfigRepo;

	@Transactional
	@Override
	public int create(RewardsConfigModel rewardsConfigModel) {
		return rewardsConfigRepo.insert(beanMapper.map(rewardsConfigModel, RewardsConfig.class));
	}

	@Transactional
	@Override
	public int createSelective(RewardsConfigModel rewardsConfigModel) {
		return rewardsConfigRepo.insertSelective(beanMapper.map(rewardsConfigModel, RewardsConfig.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return rewardsConfigRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public RewardsConfigModel findByPrimaryKey(Long id) {
		RewardsConfig rewardsConfig = rewardsConfigRepo.selectByPrimaryKey(id);
		return beanMapper.map(rewardsConfig, RewardsConfigModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(RewardsConfigModel rewardsConfigModel) {
		return rewardsConfigRepo.selectCount(beanMapper.map(rewardsConfigModel, RewardsConfig.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<RewardsConfigModel> selectPage(RewardsConfigModel rewardsConfigModel,Pageable pageable) {
		RewardsConfig rewardsConfig = beanMapper.map(rewardsConfigModel, RewardsConfig.class);
		return beanMapper.mapAsList(rewardsConfigRepo.selectPage(rewardsConfig,pageable),RewardsConfigModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(RewardsConfigModel rewardsConfigModel) {
		return rewardsConfigRepo.updateByPrimaryKey(beanMapper.map(rewardsConfigModel, RewardsConfig.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(RewardsConfigModel rewardsConfigModel) {
		return rewardsConfigRepo.updateByPrimaryKeySelective(beanMapper.map(rewardsConfigModel, RewardsConfig.class));
	}

}
