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

import com.hengsu.bhyy.core.service.CaseImageService;
import com.hengsu.bhyy.core.model.CaseImageModel;
import com.hengsu.bhyy.core.vo.CaseImageVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class CaseImageRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CaseImageRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseImageService caseImageService;

	@GetMapping(value = "/core/caseImage/{id}")
	public ResponseEnvelope<CaseImageVO> getCaseImageById(@PathVariable Long id){
		CaseImageModel caseImageModel = caseImageService.findByPrimaryKey(id);
		CaseImageVO caseImageVO =beanMapper.map(caseImageModel, CaseImageVO.class);
		ResponseEnvelope<CaseImageVO> responseEnv = new ResponseEnvelope<>(caseImageVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/caseImage")
    public ResponseEnvelope<Page<CaseImageModel>> listCaseImage(CaseImageVO caseImageVO,Pageable pageable){

		CaseImageModel param = beanMapper.map(caseImageVO, CaseImageModel.class);
        List<CaseImageModel> caseImageModelModels = caseImageService.selectPage(param,pageable);
        long count=caseImageService.selectCount(param);
        Page<CaseImageModel> page = new PageImpl<>(caseImageModelModels,pageable,count);
        ResponseEnvelope<Page<CaseImageModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/caseImage")
	public ResponseEnvelope<Integer> createCaseImage(@RequestBody CaseImageVO caseImageVO){
		CaseImageModel caseImageModel = beanMapper.map(caseImageVO, CaseImageModel.class);
		Integer  result = caseImageService.create(caseImageModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/caseImage/{id}")
	public ResponseEnvelope<Integer> deleteCaseImageByPrimaryKey(@PathVariable Long id){
		Integer  result = caseImageService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/caseImage/{id}")
	public ResponseEnvelope<Integer> updateCaseImageByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody CaseImageVO caseImageVO){
		CaseImageModel caseImageModel = beanMapper.map(caseImageVO, CaseImageModel.class);
		caseImageModel.setId(id);
		Integer  result = caseImageService.updateByPrimaryKeySelective(caseImageModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

}
