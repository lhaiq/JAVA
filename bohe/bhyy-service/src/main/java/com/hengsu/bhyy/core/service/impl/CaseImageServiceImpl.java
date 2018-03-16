package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CaseImage;
import com.hengsu.bhyy.core.repository.CaseImageRepository;
import com.hengsu.bhyy.core.model.CaseImageModel;
import com.hengsu.bhyy.core.service.CaseImageService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class CaseImageServiceImpl implements CaseImageService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseImageRepository caseImageRepo;

	@Transactional
	@Override
	public int create(CaseImageModel caseImageModel) {
		return caseImageRepo.insert(beanMapper.map(caseImageModel, CaseImage.class));
	}

	@Transactional
	@Override
	public int createSelective(CaseImageModel caseImageModel) {
		return caseImageRepo.insertSelective(beanMapper.map(caseImageModel, CaseImage.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return caseImageRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CaseImageModel findByPrimaryKey(Long id) {
		CaseImage caseImage = caseImageRepo.selectByPrimaryKey(id);
		return beanMapper.map(caseImage, CaseImageModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CaseImageModel caseImageModel) {
		return caseImageRepo.selectCount(beanMapper.map(caseImageModel, CaseImage.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CaseImageModel> selectPage(CaseImageModel caseImageModel,Pageable pageable) {
		CaseImage caseImage = beanMapper.map(caseImageModel, CaseImage.class);
		return beanMapper.mapAsList(caseImageRepo.selectPage(caseImage,pageable),CaseImageModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CaseImageModel caseImageModel) {
		return caseImageRepo.updateByPrimaryKey(beanMapper.map(caseImageModel, CaseImage.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CaseImageModel caseImageModel) {
		return caseImageRepo.updateByPrimaryKeySelective(beanMapper.map(caseImageModel, CaseImage.class));
	}

}
