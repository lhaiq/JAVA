package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CaseCategory;
import com.hengsu.bhyy.core.repository.CaseCategoryRepository;
import com.hengsu.bhyy.core.model.CaseCategoryModel;
import com.hengsu.bhyy.core.service.CaseCategoryService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class CaseCategoryServiceImpl implements CaseCategoryService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseCategoryRepository caseCategoryRepo;

	@Transactional
	@Override
	public int create(CaseCategoryModel caseCategoryModel) {
		return caseCategoryRepo.insert(beanMapper.map(caseCategoryModel, CaseCategory.class));
	}

	@Transactional
	@Override
	public int createSelective(CaseCategoryModel caseCategoryModel) {
		return caseCategoryRepo.insertSelective(beanMapper.map(caseCategoryModel, CaseCategory.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return caseCategoryRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CaseCategoryModel findByPrimaryKey(Long id) {
		CaseCategory caseCategory = caseCategoryRepo.selectByPrimaryKey(id);
		return beanMapper.map(caseCategory, CaseCategoryModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CaseCategoryModel caseCategoryModel) {
		return caseCategoryRepo.selectCount(beanMapper.map(caseCategoryModel, CaseCategory.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CaseCategoryModel> selectPage(CaseCategoryModel caseCategoryModel,Pageable pageable) {
		CaseCategory caseCategory = beanMapper.map(caseCategoryModel, CaseCategory.class);
		return beanMapper.mapAsList(caseCategoryRepo.selectPage(caseCategory,pageable),CaseCategoryModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CaseCategoryModel caseCategoryModel) {
		return caseCategoryRepo.updateByPrimaryKey(beanMapper.map(caseCategoryModel, CaseCategory.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CaseCategoryModel caseCategoryModel) {
		return caseCategoryRepo.updateByPrimaryKeySelective(beanMapper.map(caseCategoryModel, CaseCategory.class));
	}

}
