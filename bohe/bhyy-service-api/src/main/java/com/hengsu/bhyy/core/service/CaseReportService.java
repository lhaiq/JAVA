
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CaseReportModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CaseReportService {

    int create(CaseReportModel caseReportModel);

    int createSelective(CaseReportModel caseReportModel);

    CaseReportModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(CaseReportModel caseReportModel);

    int updateByPrimaryKeySelective(CaseReportModel caseReportModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(CaseReportModel caseReportModel);

    List<CaseReportModel> selectPage(CaseReportModel caseReportModel, Pageable pageable);

    Page<Map<String, Object>> primaryScreenSearch(Map<String, Object> param, Pageable pageable);

    Page<Map<String, Object>> searchCaseReport(Map<String, Object> param, Pageable pageable);


    Page<Map<String, Object>> searchDoctorCaseReport(Long doctorId, Map<String, Object> param, Pageable pageable);

    Page<Map<String, Object>> searchCustomerCaseReport(Long customerId, Map<String, Object> param, Pageable pageable);

}