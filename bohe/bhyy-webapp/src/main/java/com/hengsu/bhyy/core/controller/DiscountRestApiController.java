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

import com.hengsu.bhyy.core.service.DiscountService;
import com.hengsu.bhyy.core.model.DiscountModel;
import com.hengsu.bhyy.core.vo.DiscountVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class DiscountRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DiscountRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscountService discountService;

	@GetMapping(value = "/core/discount/{id}")
	public ResponseEnvelope<DiscountVO> getDiscountById(@PathVariable Long id){
		DiscountModel discountModel = discountService.findByPrimaryKey(id);
		DiscountVO discountVO =beanMapper.map(discountModel, DiscountVO.class);
		ResponseEnvelope<DiscountVO> responseEnv = new ResponseEnvelope<>(discountVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/discount")
    public ResponseEnvelope<Page<DiscountModel>> listDiscount(DiscountVO discountVO,Pageable pageable){

		DiscountModel param = beanMapper.map(discountVO, DiscountModel.class);
        List<DiscountModel> discountModelModels = discountService.selectPage(param,pageable);
        long count=discountService.selectCount(param);
        Page<DiscountModel> page = new PageImpl<>(discountModelModels,pageable,count);
        ResponseEnvelope<Page<DiscountModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/discount")
	public ResponseEnvelope<Integer> createDiscount(@RequestBody DiscountVO discountVO){
		DiscountModel discountModel = beanMapper.map(discountVO, DiscountModel.class);
		Integer  result = discountService.createSelective(discountModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/discount/{id}")
	public ResponseEnvelope<Integer> deleteDiscountByPrimaryKey(@PathVariable Long id){
		Integer  result = discountService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/discount/{id}")
	public ResponseEnvelope<Integer> updateDiscountByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody DiscountVO discountVO){
		DiscountModel discountModel = beanMapper.map(discountVO, DiscountModel.class);
		discountModel.setId(id);
		Integer  result = discountService.updateByPrimaryKeySelective(discountModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
