package com.hengsu.bhyy.core.controller;

import com.google.common.cache.Cache;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.annotation.IgnoreAuth;
import com.hengsu.bhyy.core.model.*;
import com.hengsu.bhyy.core.service.SysMenuService;
import com.hengsu.bhyy.core.service.SysRoleService;
import com.hengsu.bhyy.core.vo.DoctorVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.hengsu.bhyy.core.service.SysUserService;
import com.hengsu.bhyy.core.vo.SysUserVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bhyy")
public class SysUserRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SysUserRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	@Qualifier("sessionCache")
	private Cache<String, SessionUserModel> sessionCache;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysMenuService sysMenuService;



	@GetMapping(value = "/core/sysUser/{id}")
	public ResponseEnvelope<SysUserVO> getSysUserById(@PathVariable Long id){
		SysUserModel sysUserModel = sysUserService.findByPrimaryKey(id);
		SysUserVO sysUserVO =beanMapper.map(sysUserModel, SysUserVO.class);
		ResponseEnvelope<SysUserVO> responseEnv = new ResponseEnvelope<>(sysUserVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/sysUser")
    public ResponseEnvelope<Page<SysUserModel>> listSysUser(SysUserVO sysUserVO,Pageable pageable){

		SysUserModel param = beanMapper.map(sysUserVO, SysUserModel.class);
        List<SysUserModel> sysUserModelModels = sysUserService.selectPage(param,pageable);
        long count=sysUserService.selectCount(param);
        Page<SysUserModel> page = new PageImpl<>(sysUserModelModels,pageable,count);
        ResponseEnvelope<Page<SysUserModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }


	/**
	 * 搜索
	 * @param param
	 * @param pageable
	 * @return
     */
	@PostMapping(value = "/core/sysUser/search")
	public ResponseEnvelope<Page<Map<String,Object>>> searchSysUser(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String,Object>> page = sysUserService.searchPage(param,pageable);
		ResponseEnvelope<Page<Map<String,Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@PostMapping(value = "/core/sysUser")
	public ResponseEnvelope<Integer> createSysUser(@RequestBody SysUserVO sysUserVO){
		SysUserModel sysUserModel = beanMapper.map(sysUserVO, SysUserModel.class);
		sysUserModel.setPassword(DigestUtils.md5Hex(sysUserModel.getPassword()));
		Integer  result = sysUserService.createSelective(sysUserModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/sysUser/{id}")
	public ResponseEnvelope<Integer> deleteSysUserByPrimaryKey(@PathVariable Long id){
		Integer  result = sysUserService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/sysUser/{id}")
	public ResponseEnvelope<Integer> updateSysUserByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody SysUserVO sysUserVO){
		SysUserModel sysUserModel = beanMapper.map(sysUserVO, SysUserModel.class);
		sysUserModel.setId(id);
		if(StringUtils.isNotEmpty(sysUserModel.getPassword())){
			sysUserModel.setPassword(DigestUtils.md5Hex(sysUserModel.getPassword()));
		}
		Integer  result = sysUserService.updateByPrimaryKeySelective(sysUserModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

	@IgnoreAuth
	@PostMapping(value = "/sysUser/login")
	public ResponseEnvelope<SysUserVO> sysUserLogin(@RequestBody Map<String, String> requestParam) {


		//判断用户存在否
		SysUserModel param = new SysUserModel();
		param.setUsername(requestParam.get("username"));
		param.setPassword(DigestUtils.md5Hex(requestParam.get("password")));
		List<SysUserModel> sysUserModels = sysUserService.selectPage(param, new PageRequest(0, 1));

		if (CollectionUtils.isEmpty(sysUserModels)) {
			HRErrorCode.throwBusinessException(HRErrorCode.ACCOUNT_ERROR);
		}

		SysUserModel sysUserModel = sysUserModels.get(0);

		String sessionId = RandomUtil.generateAuthToken();
		sessionCache.put(sessionId, new SessionUserModel(sysUserModel.getId(),SessionUserModel.ROLE_ADMIN));

		SysUserVO sysUserVO = beanMapper.map(sysUserModel, SysUserVO.class);
		sysUserVO.setSessionId(sessionId);

		//查询角色与权限
		SysRoleModel sysRoleModel = sysRoleService.findByUserId(sysUserModel.getId());
		sysUserVO.setRoleId(sysRoleModel.getId());
		sysUserVO.setRoleName(sysRoleModel.getName());
		List<SysMenuModel> sysMenuModels = sysMenuService.selectByRoleId(sysRoleModel.getId());
		List<Long> menuIds = sysMenuModels.stream().map(e->e.getId()).collect(Collectors.toList());
		sysUserVO.setMenuIds(menuIds);
		ResponseEnvelope<SysUserVO> responseEnv = new ResponseEnvelope<>(sysUserVO, true);
		return responseEnv;
	}


	@GetMapping(value = "/user/logout")
	public ResponseEnvelope<Integer> userLogout(@RequestHeader("Authorization") String sessionId) {
		sessionCache.invalidate(sessionId);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
		return responseEnv;
	}

}
