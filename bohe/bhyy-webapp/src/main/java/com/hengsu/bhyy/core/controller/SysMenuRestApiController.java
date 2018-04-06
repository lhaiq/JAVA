package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.model.SysRoleModel;
import com.hengsu.bhyy.core.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bhyy")
public class SysMenuRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SysMenuRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping(value = "/core/sysMenu/{id}")
	public ResponseEnvelope<SysMenuVO> getSysMenuById(@PathVariable Long id){
		SysMenuModel sysMenuModel = sysMenuService.findByPrimaryKey(id);
		SysMenuVO sysMenuVO =beanMapper.map(sysMenuModel, SysMenuVO.class);
		ResponseEnvelope<SysMenuVO> responseEnv = new ResponseEnvelope<>(sysMenuVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/sysMenuIds")
	public ResponseEnvelope<List<Long>> listMenu(@RequestAttribute("userId") Long userId){
		SysRoleModel sysRoleModel = sysRoleService.findByUserId(userId);
		List<SysMenuModel> sysMenuModels = sysMenuService.selectByRoleId(sysRoleModel.getId());
		List<Long> menuIds = sysMenuModels.stream().map(e->e.getId()).collect(Collectors.toList());
		ResponseEnvelope<List<Long>> responseEnv = new ResponseEnvelope<>(menuIds,true);
		return responseEnv;
	}

	/**
	 * 菜单列表
	 * @return
     */
	@GetMapping(value = "/core/sysMenu")
    public ResponseEnvelope<List<SysMenuVO>> listSysMenu(){
		SysMenuModel param = new SysMenuModel();
        List<SysMenuModel> sysMenuModelModels = sysMenuService.selectPage(param,new PageRequest(0,Integer.MAX_VALUE));
		List<SysMenuVO> menuVOs = new ArrayList<>();
		for(SysMenuModel sysMenuModel:sysMenuModelModels){
			if(0==sysMenuModel.getParentId()){
				SysMenuVO sysMenuVO = beanMapper.map(sysMenuModel,SysMenuVO.class);
				List<SysMenuVO> children = new ArrayList<>();
				for(SysMenuModel sysMenuModel2:sysMenuModelModels){
					if(sysMenuModel2.getParentId().equals(sysMenuModel.getId())){
						children.add(beanMapper.map(sysMenuModel2,SysMenuVO.class));
					}
				}
				sysMenuVO.setChildren(children);
				menuVOs.add(sysMenuVO);
			}
		}
        ResponseEnvelope<List<SysMenuVO>> responseEnv = new ResponseEnvelope<>(menuVOs,true);
        return responseEnv;
    }

//	@PostMapping(value = "/core/sysMenu")
//	public ResponseEnvelope<Integer> createSysMenu(@RequestBody SysMenuVO sysMenuVO){
//		SysMenuModel sysMenuModel = beanMapper.map(sysMenuVO, SysMenuModel.class);
//		Integer  result = sysMenuService.create(sysMenuModel);
//		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
//        return responseEnv;
//	}
//
//    @DeleteMapping(value = "/core/sysMenu/{id}")
//	public ResponseEnvelope<Integer> deleteSysMenuByPrimaryKey(@PathVariable Long id){
//		Integer  result = sysMenuService.deleteByPrimaryKey(id);
//		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
//        return responseEnv;
//	}
//
//
//    @PutMapping(value = "/core/sysMenu/{id}")
//	public ResponseEnvelope<Integer> updateSysMenuByPrimaryKeySelective(@PathVariable Long id,
//					@RequestBody SysMenuVO sysMenuVO){
//		SysMenuModel sysMenuModel = beanMapper.map(sysMenuVO, SysMenuModel.class);
//		sysMenuModel.setId(id);
//		Integer  result = sysMenuService.updateByPrimaryKeySelective(sysMenuModel);
//		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
//        return responseEnv;
//	}

}
