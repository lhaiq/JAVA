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

import com.hengsu.bhyy.core.service.BillItemService;
import com.hengsu.bhyy.core.model.BillItemModel;
import com.hengsu.bhyy.core.vo.BillItemVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class BillItemRestApiController {

	private final Logger logger = LoggerFactory.getLogger(BillItemRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BillItemService billItemService;

	@GetMapping(value = "/core/billItem/{id}")
	public ResponseEnvelope<BillItemVO> getBillItemById(@PathVariable Long id){
		BillItemModel billItemModel = billItemService.findByPrimaryKey(id);
		BillItemVO billItemVO =beanMapper.map(billItemModel, BillItemVO.class);
		ResponseEnvelope<BillItemVO> responseEnv = new ResponseEnvelope<>(billItemVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/billItem")
    public ResponseEnvelope<Page<BillItemModel>> listBillItem(BillItemVO billItemVO,Pageable pageable){

		BillItemModel param = beanMapper.map(billItemVO, BillItemModel.class);
        List<BillItemModel> billItemModelModels = billItemService.selectPage(param,pageable);
        long count=billItemService.selectCount(param);
        Page<BillItemModel> page = new PageImpl<>(billItemModelModels,pageable,count);
        ResponseEnvelope<Page<BillItemModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/billItem")
	public ResponseEnvelope<Integer> createBillItem(@RequestBody BillItemVO billItemVO){
		BillItemModel billItemModel = beanMapper.map(billItemVO, BillItemModel.class);
		Integer  result = billItemService.create(billItemModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/billItem/{id}")
	public ResponseEnvelope<Integer> deleteBillItemByPrimaryKey(@PathVariable Long id){
		Integer  result = billItemService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/billItem/{id}")
	public ResponseEnvelope<Integer> updateBillItemByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody BillItemVO billItemVO){
		BillItemModel billItemModel = beanMapper.map(billItemVO, BillItemModel.class);
		billItemModel.setId(id);
		Integer  result = billItemService.updateByPrimaryKeySelective(billItemModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
