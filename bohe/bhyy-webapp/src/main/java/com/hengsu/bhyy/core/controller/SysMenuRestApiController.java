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

import com.hengsu.bhyy.core.service.SysMenuService;
import com.hengsu.bhyy.core.model.SysMenuModel;
import com.hengsu.bhyy.core.vo.SysMenuVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class SysMenuRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SysMenuRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysMenuService sysMenuService;

	@GetMapping(value = "/core/sysMenu/{id}")
	public ResponseEnvelope<SysMenuVO> getSysMenuById(@PathVariable Long id){
		SysMenuModel sysMenuModel = sysMenuService.findByPrimaryKey(id);
		SysMenuVO sysMenuVO =beanMapper.map(sysMenuModel, SysMenuVO.class);
		ResponseEnvelope<SysMenuVO> responseEnv = new ResponseEnvelope<>(sysMenuVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/sysMenu")
    public ResponseEnvelope<Page<SysMenuModel>> listSysMenu(SysMenuVO sysMenuVO,Pageable pageable){
		SysMenuModel param = beanMapper.map(sysMenuVO, SysMenuModel.class);
        List<SysMenuModel> sysMenuModelModels = sysMenuService.selectPage(param,pageable);
        long count=sysMenuService.selectCount(param);
        Page<SysMenuModel> page = new PageImpl<>(sysMenuModelModels,pageable,count);
        ResponseEnvelope<Page<SysMenuModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/sysMenu")
	public ResponseEnvelope<Integer> createSysMenu(@RequestBody SysMenuVO sysMenuVO){
		SysMenuModel sysMenuModel = beanMapper.map(sysMenuVO, SysMenuModel.class);
		Integer  result = sysMenuService.create(sysMenuModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/sysMenu/{id}")
	public ResponseEnvelope<Integer> deleteSysMenuByPrimaryKey(@PathVariable Long id){
		Integer  result = sysMenuService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/sysMenu/{id}")
	public ResponseEnvelope<Integer> updateSysMenuByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody SysMenuVO sysMenuVO){
		SysMenuModel sysMenuModel = beanMapper.map(sysMenuVO, SysMenuModel.class);
		sysMenuModel.setId(id);
		Integer  result = sysMenuService.updateByPrimaryKeySelective(sysMenuModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
