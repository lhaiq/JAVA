
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CaseReportModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CaseReportService{

public int create(CaseReportModel caseReportModel);

public int createSelective(CaseReportModel caseReportModel);

public CaseReportModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(CaseReportModel caseReportModel);

public int updateByPrimaryKeySelective(CaseReportModel caseReportModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(CaseReportModel caseReportModel);

public List<CaseReportModel> selectPage(CaseReportModel caseReportModel, Pageable pageable);

}