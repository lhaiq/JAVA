package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.ReferralRelation;
import com.hengsu.bhyy.core.repository.ReferralRelationRepository;
import com.hengsu.bhyy.core.model.ReferralRelationModel;
import com.hengsu.bhyy.core.service.ReferralRelationService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class ReferralRelationServiceImpl implements ReferralRelationService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ReferralRelationRepository referralRelationRepo;

	@Transactional
	@Override
	public int create(ReferralRelationModel referralRelationModel) {
		return referralRelationRepo.insert(beanMapper.map(referralRelationModel, ReferralRelation.class));
	}

	@Transactional
	@Override
	public int createSelective(ReferralRelationModel referralRelationModel) {
		return referralRelationRepo.insertSelective(beanMapper.map(referralRelationModel, ReferralRelation.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return referralRelationRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ReferralRelationModel findByPrimaryKey(Long id) {
		ReferralRelation referralRelation = referralRelationRepo.selectByPrimaryKey(id);
		return beanMapper.map(referralRelation, ReferralRelationModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(ReferralRelationModel referralRelationModel) {
		return referralRelationRepo.selectCount(beanMapper.map(referralRelationModel, ReferralRelation.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReferralRelationModel> selectPage(ReferralRelationModel referralRelationModel,Pageable pageable) {
		ReferralRelation referralRelation = beanMapper.map(referralRelationModel, ReferralRelation.class);
		return beanMapper.mapAsList(referralRelationRepo.selectPage(referralRelation,pageable),ReferralRelationModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(ReferralRelationModel referralRelationModel) {
		return referralRelationRepo.updateByPrimaryKey(beanMapper.map(referralRelationModel, ReferralRelation.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(ReferralRelationModel referralRelationModel) {
		return referralRelationRepo.updateByPrimaryKeySelective(beanMapper.map(referralRelationModel, ReferralRelation.class));
	}

}
