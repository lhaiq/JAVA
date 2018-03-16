package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CaseTemplate;
import com.hengsu.bhyy.core.repository.CaseTemplateRepository;
import com.hengsu.bhyy.core.model.CaseTemplateModel;
import com.hengsu.bhyy.core.service.CaseTemplateService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class CaseTemplateServiceImpl implements CaseTemplateService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseTemplateRepository caseTemplateRepo;

	@Transactional
	@Override
	public int create(CaseTemplateModel caseTemplateModel) {
		return caseTemplateRepo.insert(beanMapper.map(caseTemplateModel, CaseTemplate.class));
	}

	@Transactional
	@Override
	public int createSelective(CaseTemplateModel caseTemplateModel) {
		return caseTemplateRepo.insertSelective(beanMapper.map(caseTemplateModel, CaseTemplate.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return caseTemplateRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CaseTemplateModel findByPrimaryKey(Long id) {
		CaseTemplate caseTemplate = caseTemplateRepo.selectByPrimaryKey(id);
		return beanMapper.map(caseTemplate, CaseTemplateModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CaseTemplateModel caseTemplateModel) {
		return caseTemplateRepo.selectCount(beanMapper.map(caseTemplateModel, CaseTemplate.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CaseTemplateModel> selectPage(CaseTemplateModel caseTemplateModel,Pageable pageable) {
		CaseTemplate caseTemplate = beanMapper.map(caseTemplateModel, CaseTemplate.class);
		return beanMapper.mapAsList(caseTemplateRepo.selectPage(caseTemplate,pageable),CaseTemplateModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CaseTemplateModel caseTemplateModel) {
		return caseTemplateRepo.updateByPrimaryKey(beanMapper.map(caseTemplateModel, CaseTemplate.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CaseTemplateModel caseTemplateModel) {
		return caseTemplateRepo.updateByPrimaryKeySelective(beanMapper.map(caseTemplateModel, CaseTemplate.class));
	}

}
