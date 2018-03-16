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

import com.hengsu.bhyy.core.service.SysRoleService;
import com.hengsu.bhyy.core.model.SysRoleModel;
import com.hengsu.bhyy.core.vo.SysRoleVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class SysRoleRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SysRoleRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping(value = "/core/sysRole/{id}")
	public ResponseEnvelope<SysRoleVO> getSysRoleById(@PathVariable Long id){
		SysRoleModel sysRoleModel = sysRoleService.findByPrimaryKey(id);
		SysRoleVO sysRoleVO =beanMapper.map(sysRoleModel, SysRoleVO.class);
		ResponseEnvelope<SysRoleVO> responseEnv = new ResponseEnvelope<>(sysRoleVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/sysRole")
    public ResponseEnvelope<Page<SysRoleModel>> listSysRole(SysRoleVO sysRoleVO,Pageable pageable){

		SysRoleModel param = beanMapper.map(sysRoleVO, SysRoleModel.class);
        List<SysRoleModel> sysRoleModelModels = sysRoleService.selectPage(param,pageable);
        long count=sysRoleService.selectCount(param);
        Page<SysRoleModel> page = new PageImpl<>(sysRoleModelModels,pageable,count);
        ResponseEnvelope<Page<SysRoleModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/sysRole")
	public ResponseEnvelope<Integer> createSysRole(@RequestBody SysRoleVO sysRoleVO){
		SysRoleModel sysRoleModel = beanMapper.map(sysRoleVO, SysRoleModel.class);
		Integer  result = sysRoleService.createSelective(sysRoleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/sysRole/{id}")
	public ResponseEnvelope<Integer> deleteSysRoleByPrimaryKey(@PathVariable Long id){
		Integer  result = sysRoleService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/sysRole/{id}")
	public ResponseEnvelope<Integer> updateSysRoleByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody SysRoleVO sysRoleVO){
		SysRoleModel sysRoleModel = beanMapper.map(sysRoleVO, SysRoleModel.class);
		sysRoleModel.setId(id);
		Integer  result = sysRoleService.updateByPrimaryKeySelective(sysRoleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
