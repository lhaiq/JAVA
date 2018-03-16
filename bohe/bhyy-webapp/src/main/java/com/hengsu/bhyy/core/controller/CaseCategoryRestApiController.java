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

import com.hengsu.bhyy.core.service.CaseCategoryService;
import com.hengsu.bhyy.core.model.CaseCategoryModel;
import com.hengsu.bhyy.core.vo.CaseCategoryVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class CaseCategoryRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CaseCategoryRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseCategoryService caseCategoryService;

	@GetMapping(value = "/core/caseCategory/{id}")
	public ResponseEnvelope<CaseCategoryVO> getCaseCategoryById(@PathVariable Long id){
		CaseCategoryModel caseCategoryModel = caseCategoryService.findByPrimaryKey(id);
		CaseCategoryVO caseCategoryVO =beanMapper.map(caseCategoryModel, CaseCategoryVO.class);
		ResponseEnvelope<CaseCategoryVO> responseEnv = new ResponseEnvelope<>(caseCategoryVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/caseCategory")
    public ResponseEnvelope<Page<CaseCategoryModel>> listCaseCategory(CaseCategoryVO caseCategoryVO,Pageable pageable){

		CaseCategoryModel param = beanMapper.map(caseCategoryVO, CaseCategoryModel.class);
        List<CaseCategoryModel> caseCategoryModelModels = caseCategoryService.selectPage(param,pageable);
        long count=caseCategoryService.selectCount(param);
        Page<CaseCategoryModel> page = new PageImpl<>(caseCategoryModelModels,pageable,count);
        ResponseEnvelope<Page<CaseCategoryModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/caseCategory")
	public ResponseEnvelope<Integer> createCaseCategory(@RequestBody CaseCategoryVO caseCategoryVO){
		CaseCategoryModel caseCategoryModel = beanMapper.map(caseCategoryVO, CaseCategoryModel.class);
		Integer  result = caseCategoryService.createSelective(caseCategoryModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/caseCategory/{id}")
	public ResponseEnvelope<Integer> deleteCaseCategoryByPrimaryKey(@PathVariable Long id){
		Integer  result = caseCategoryService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/caseCategory/{id}")
	public ResponseEnvelope<Integer> updateCaseCategoryByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody CaseCategoryVO caseCategoryVO){
		CaseCategoryModel caseCategoryModel = beanMapper.map(caseCategoryVO, CaseCategoryModel.class);
		caseCategoryModel.setId(id);
		Integer  result = caseCategoryService.updateByPrimaryKeySelective(caseCategoryModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
