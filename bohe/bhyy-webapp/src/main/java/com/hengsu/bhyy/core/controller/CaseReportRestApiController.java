package com.hengsu.bhyy.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import com.wlw.pylon.web.rest.annotation.RestApiController;

import com.hengsu.bhyy.core.service.CaseReportService;
import com.hengsu.bhyy.core.model.CaseReportModel;
import com.hengsu.bhyy.core.vo.CaseReportVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class CaseReportRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CaseReportRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseReportService caseReportService;

	@GetMapping(value = "/core/caseReport/{id}")
	public ResponseEnvelope<CaseReportVO> getCaseReportById(@PathVariable Long id){
		CaseReportModel caseReportModel = caseReportService.findByPrimaryKey(id);
		CaseReportVO caseReportVO =beanMapper.map(caseReportModel, CaseReportVO.class);
		ResponseEnvelope<CaseReportVO> responseEnv = new ResponseEnvelope<>(caseReportVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/caseReport")
    public ResponseEnvelope<Page<CaseReportModel>> listCaseReport(CaseReportVO caseReportVO,Pageable pageable){

		CaseReportModel param = beanMapper.map(caseReportVO, CaseReportModel.class);
        List<CaseReportModel> caseReportModelModels = caseReportService.selectPage(param,pageable);
        long count=caseReportService.selectCount(param);
        Page<CaseReportModel> page = new PageImpl<>(caseReportModelModels,pageable,count);
        ResponseEnvelope<Page<CaseReportModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/caseReport")
	public ResponseEnvelope<Integer> createCaseReport(@RequestBody CaseReportVO caseReportVO){
		CaseReportModel caseReportModel = beanMapper.map(caseReportVO, CaseReportModel.class);
		Integer  result = caseReportService.create(caseReportModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/caseReport/{id}")
	public ResponseEnvelope<Integer> deleteCaseReportByPrimaryKey(@PathVariable Long id){
		Integer  result = caseReportService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/caseReport/{id}")
	public ResponseEnvelope<Integer> updateCaseReportByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody CaseReportVO caseReportVO){
		CaseReportModel caseReportModel = beanMapper.map(caseReportVO, CaseReportModel.class);
		caseReportModel.setId(id);
		Integer  result = caseReportService.updateByPrimaryKeySelective(caseReportModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
