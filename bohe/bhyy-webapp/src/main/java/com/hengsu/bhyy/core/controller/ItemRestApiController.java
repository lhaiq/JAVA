package com.hengsu.bhyy.core.controller;

import org.apache.commons.lang3.StringUtils;
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

import com.hengsu.bhyy.core.service.ItemService;
import com.hengsu.bhyy.core.model.ItemModel;
import com.hengsu.bhyy.core.vo.ItemVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class ItemRestApiController {

	private final Logger logger = LoggerFactory.getLogger(ItemRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ItemService itemService;

	@GetMapping(value = "/core/item/{id}")
	public ResponseEnvelope<ItemVO> getItemById(@PathVariable Long id){
		ItemModel itemModel = itemService.findByPrimaryKey(id);
		ItemVO itemVO =beanMapper.map(itemModel, ItemVO.class);
		ResponseEnvelope<ItemVO> responseEnv = new ResponseEnvelope<>(itemVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/item")
    public ResponseEnvelope<Page<ItemModel>> listItem(ItemVO itemVO,Pageable pageable) throws Exception{

		if(StringUtils.isNotEmpty(itemVO.getName())){
			String name = new String(itemVO.getName().getBytes("iso-8859-1"), "utf-8");
			itemVO.setName(name);
		}
		ItemModel param = beanMapper.map(itemVO, ItemModel.class);
        List<ItemModel> itemModelModels = itemService.selectPage(param,pageable);
        long count=itemService.selectCount(param);
        Page<ItemModel> page = new PageImpl<>(itemModelModels,pageable,count);
        ResponseEnvelope<Page<ItemModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/item")
	public ResponseEnvelope<Integer> createItem(@RequestBody ItemVO itemVO){
		ItemModel itemModel = beanMapper.map(itemVO, ItemModel.class);
		Integer  result = itemService.createSelective(itemModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/item/{id}")
	public ResponseEnvelope<Integer> deleteItemByPrimaryKey(@PathVariable Long id){
		Integer  result = itemService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/item/{id}")
	public ResponseEnvelope<Integer> updateItemByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody ItemVO itemVO){
		ItemModel itemModel = beanMapper.map(itemVO, ItemModel.class);
		itemModel.setId(id);
		Integer  result = itemService.updateByPrimaryKeySelective(itemModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
