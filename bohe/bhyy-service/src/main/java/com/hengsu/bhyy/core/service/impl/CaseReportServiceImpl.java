package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CaseReport;
import com.hengsu.bhyy.core.repository.CaseReportRepository;
import com.hengsu.bhyy.core.model.CaseReportModel;
import com.hengsu.bhyy.core.service.CaseReportService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class CaseReportServiceImpl implements CaseReportService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseReportRepository caseReportRepo;

	@Transactional
	@Override
	public int create(CaseReportModel caseReportModel) {
		return caseReportRepo.insert(beanMapper.map(caseReportModel, CaseReport.class));
	}

	@Transactional
	@Override
	public int createSelective(CaseReportModel caseReportModel) {
		return caseReportRepo.insertSelective(beanMapper.map(caseReportModel, CaseReport.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return caseReportRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CaseReportModel findByPrimaryKey(Long id) {
		CaseReport caseReport = caseReportRepo.selectByPrimaryKey(id);
		return beanMapper.map(caseReport, CaseReportModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CaseReportModel caseReportModel) {
		return caseReportRepo.selectCount(beanMapper.map(caseReportModel, CaseReport.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CaseReportModel> selectPage(CaseReportModel caseReportModel,Pageable pageable) {
		CaseReport caseReport = beanMapper.map(caseReportModel, CaseReport.class);
		return beanMapper.mapAsList(caseReportRepo.selectPage(caseReport,pageable),CaseReportModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CaseReportModel caseReportModel) {
		return caseReportRepo.updateByPrimaryKey(beanMapper.map(caseReportModel, CaseReport.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CaseReportModel caseReportModel) {
		return caseReportRepo.updateByPrimaryKeySelective(beanMapper.map(caseReportModel, CaseReport.class));
	}

}
