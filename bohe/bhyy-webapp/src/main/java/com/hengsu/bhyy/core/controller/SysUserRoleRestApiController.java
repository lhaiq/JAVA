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

import com.hengsu.bhyy.core.service.SysUserRoleService;
import com.hengsu.bhyy.core.model.SysUserRoleModel;
import com.hengsu.bhyy.core.vo.SysUserRoleVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class SysUserRoleRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SysUserRoleRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@GetMapping(value = "/core/sysUserRole/{id}")
	public ResponseEnvelope<SysUserRoleVO> getSysUserRoleById(@PathVariable Long id){
		SysUserRoleModel sysUserRoleModel = sysUserRoleService.findByPrimaryKey(id);
		SysUserRoleVO sysUserRoleVO =beanMapper.map(sysUserRoleModel, SysUserRoleVO.class);
		ResponseEnvelope<SysUserRoleVO> responseEnv = new ResponseEnvelope<>(sysUserRoleVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/sysUserRole")
    public ResponseEnvelope<Page<SysUserRoleModel>> listSysUserRole(SysUserRoleVO sysUserRoleVO,Pageable pageable){

		SysUserRoleModel param = beanMapper.map(sysUserRoleVO, SysUserRoleModel.class);
        List<SysUserRoleModel> sysUserRoleModelModels = sysUserRoleService.selectPage(param,pageable);
        long count=sysUserRoleService.selectCount(param);
        Page<SysUserRoleModel> page = new PageImpl<>(sysUserRoleModelModels,pageable,count);
        ResponseEnvelope<Page<SysUserRoleModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/sysUserRole")
	public ResponseEnvelope<Integer> createSysUserRole(@RequestBody SysUserRoleVO sysUserRoleVO){
		SysUserRoleModel sysUserRoleModel = beanMapper.map(sysUserRoleVO, SysUserRoleModel.class);
		Integer  result = sysUserRoleService.create(sysUserRoleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/sysUserRole/{id}")
	public ResponseEnvelope<Integer> deleteSysUserRoleByPrimaryKey(@PathVariable Long id){
		Integer  result = sysUserRoleService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/sysUserRole/{id}")
	public ResponseEnvelope<Integer> updateSysUserRoleByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody SysUserRoleVO sysUserRoleVO){
		SysUserRoleModel sysUserRoleModel = beanMapper.map(sysUserRoleVO, SysUserRoleModel.class);
		sysUserRoleModel.setId(id);
		Integer  result = sysUserRoleService.updateByPrimaryKeySelective(sysUserRoleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
