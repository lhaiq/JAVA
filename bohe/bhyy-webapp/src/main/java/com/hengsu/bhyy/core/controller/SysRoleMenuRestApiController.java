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

import com.hengsu.bhyy.core.service.SysRoleMenuService;
import com.hengsu.bhyy.core.model.SysRoleMenuModel;
import com.hengsu.bhyy.core.vo.SysRoleMenuVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class SysRoleMenuRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SysRoleMenuRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@GetMapping(value = "/core/sysRoleMenu/{id}")
	public ResponseEnvelope<SysRoleMenuVO> getSysRoleMenuById(@PathVariable Long id){
		SysRoleMenuModel sysRoleMenuModel = sysRoleMenuService.findByPrimaryKey(id);
		SysRoleMenuVO sysRoleMenuVO =beanMapper.map(sysRoleMenuModel, SysRoleMenuVO.class);
		ResponseEnvelope<SysRoleMenuVO> responseEnv = new ResponseEnvelope<>(sysRoleMenuVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/sysRoleMenu")
    public ResponseEnvelope<Page<SysRoleMenuModel>> listSysRoleMenu(SysRoleMenuVO sysRoleMenuVO,Pageable pageable){

		SysRoleMenuModel param = beanMapper.map(sysRoleMenuVO, SysRoleMenuModel.class);
        List<SysRoleMenuModel> sysRoleMenuModelModels = sysRoleMenuService.selectPage(param,pageable);
        long count=sysRoleMenuService.selectCount(param);
        Page<SysRoleMenuModel> page = new PageImpl<>(sysRoleMenuModelModels,pageable,count);
        ResponseEnvelope<Page<SysRoleMenuModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/sysRoleMenu")
	public ResponseEnvelope<Integer> createSysRoleMenu(@RequestBody SysRoleMenuVO sysRoleMenuVO){
		SysRoleMenuModel sysRoleMenuModel = beanMapper.map(sysRoleMenuVO, SysRoleMenuModel.class);
		Integer  result = sysRoleMenuService.create(sysRoleMenuModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/sysRoleMenu/{id}")
	public ResponseEnvelope<Integer> deleteSysRoleMenuByPrimaryKey(@PathVariable Long id){
		Integer  result = sysRoleMenuService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/sysRoleMenu/{id}")
	public ResponseEnvelope<Integer> updateSysRoleMenuByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody SysRoleMenuVO sysRoleMenuVO){
		SysRoleMenuModel sysRoleMenuModel = beanMapper.map(sysRoleMenuVO, SysRoleMenuModel.class);
		sysRoleMenuModel.setId(id);
		Integer  result = sysRoleMenuService.updateByPrimaryKeySelective(sysRoleMenuModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
