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

import com.hengsu.bhyy.core.service.ItemClassService;
import com.hengsu.bhyy.core.model.ItemClassModel;
import com.hengsu.bhyy.core.vo.ItemClassVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class ItemClassRestApiController {

	private final Logger logger = LoggerFactory.getLogger(ItemClassRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ItemClassService itemClassService;

	@GetMapping(value = "/core/itemClass/{id}")
	public ResponseEnvelope<ItemClassVO> getItemClassById(@PathVariable Long id){
		ItemClassModel itemClassModel = itemClassService.findByPrimaryKey(id);
		ItemClassVO itemClassVO =beanMapper.map(itemClassModel, ItemClassVO.class);
		ResponseEnvelope<ItemClassVO> responseEnv = new ResponseEnvelope<>(itemClassVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/itemClass")
    public ResponseEnvelope<Page<ItemClassModel>> listItemClass(ItemClassVO itemClassVO,Pageable pageable){

		ItemClassModel param = beanMapper.map(itemClassVO, ItemClassModel.class);
        List<ItemClassModel> itemClassModelModels = itemClassService.selectPage(param,pageable);
        long count=itemClassService.selectCount(param);
        Page<ItemClassModel> page = new PageImpl<>(itemClassModelModels,pageable,count);
        ResponseEnvelope<Page<ItemClassModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@GetMapping(value = "/core/itemClass/type")
	public ResponseEnvelope<Page<Map<String,Object>>> selectByType(@RequestParam int type,Pageable pageable){
		Page<Map<String,Object>> page = itemClassService.selectByType(type,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@PostMapping(value = "/core/itemClass")
	public ResponseEnvelope<Integer> createItemClass(@RequestBody ItemClassVO itemClassVO){
		ItemClassModel itemClassModel = beanMapper.map(itemClassVO, ItemClassModel.class);
		itemClassModel.setCreateTime(new Date());
		Integer  result = itemClassService.createSelective(itemClassModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/itemClass/{id}")
	public ResponseEnvelope<Integer> deleteItemClassByPrimaryKey(@PathVariable Long id){
		Integer  result = itemClassService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/itemClass/{id}")
	public ResponseEnvelope<Integer> updateItemClassByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody ItemClassVO itemClassVO){
		ItemClassModel itemClassModel = beanMapper.map(itemClassVO, ItemClassModel.class);
		itemClassModel.setId(id);
		Integer  result = itemClassService.updateByPrimaryKeySelective(itemClassModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
