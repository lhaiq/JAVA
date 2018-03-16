package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.service.CustomerService;
import com.hengsu.bhyy.core.vo.CustomerVO;
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

import com.hengsu.bhyy.core.service.CustomerRelationService;
import com.hengsu.bhyy.core.model.CustomerRelationModel;
import com.hengsu.bhyy.core.vo.CustomerRelationVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class CustomerRelationRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CustomerRelationRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CustomerRelationService customerRelationService;

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/core/customerRelation/{id}")
	public ResponseEnvelope<CustomerRelationVO> getCustomerRelationById(@PathVariable Long id){
		CustomerRelationModel customerRelationModel = customerRelationService.findByPrimaryKey(id);
		CustomerRelationVO customerRelationVO =beanMapper.map(customerRelationModel, CustomerRelationVO.class);
		ResponseEnvelope<CustomerRelationVO> responseEnv = new ResponseEnvelope<>(customerRelationVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/customerRelation")
	public ResponseEnvelope<List<Map<String,Object>> > customerRelation(@RequestParam("id") Long id){
		List<Map<String,Object>>  customerModelModels = customerService.selectRelation(id);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(customerModelModels,true);
		return responseEnv;
	}

	//添加关系
	@PostMapping(value = "/core/{customerId}/customerRelation")
	public ResponseEnvelope<Integer> createCustomerRelation(
			@PathVariable Long customerId,
			@RequestBody CustomerVO customerVO){
		CustomerModel customerModel = beanMapper.map(customerVO,CustomerModel.class);
		customerModel.setType(CustomerModel.TYPE_RELATION);
		customerRelationService.addRelation(customerId,customerModel,customerVO.getRelation());
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1,true);
        return responseEnv;
	}

	//更新关联客户
	@PutMapping(value = "/core/{customerId}/customerRelation/{relationId}")
	public ResponseEnvelope<Integer> updateCustomerRelation(
			@PathVariable Long customerId,
			@PathVariable Long relationId,
			@RequestBody CustomerVO customerVO){
		CustomerModel customerModel = beanMapper.map(customerVO,CustomerModel.class);
		customerRelationService.updateRelation(customerId,relationId,customerModel,customerVO.getRelation());
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1,true);
		return responseEnv;
	}


	@GetMapping(value = "/core/{customerId}/customerRelation/{relationId}")
	public ResponseEnvelope<CustomerModel> getCustomerRelation(
			@PathVariable Long customerId,
			@PathVariable Long relationId){
		CustomerModel customerModel = customerService.findByPrimaryKey(customerId);
		CustomerRelationModel customerRelationModel = customerRelationService.findByPrimaryKey(relationId);
		customerModel.setRelation(customerRelationModel.getRelation());
		ResponseEnvelope<CustomerModel> responseEnv = new ResponseEnvelope<>(customerModel,true);
		return responseEnv;
	}

    @DeleteMapping(value = "/core/customerRelation/{id}")
	public ResponseEnvelope<Integer> deleteCustomer(@PathVariable Long id){
		Integer  result = customerRelationService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}





}
