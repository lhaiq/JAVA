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

import com.hengsu.bhyy.core.service.BannerService;
import com.hengsu.bhyy.core.model.BannerModel;
import com.hengsu.bhyy.core.vo.BannerVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class BannerRestApiController {

	private final Logger logger = LoggerFactory.getLogger(BannerRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BannerService bannerService;

	@GetMapping(value = "/core/banner/{id}")
	public ResponseEnvelope<BannerVO> getBannerById(@PathVariable Long id){
		BannerModel bannerModel = bannerService.findByPrimaryKey(id);
		BannerVO bannerVO =beanMapper.map(bannerModel, BannerVO.class);
		ResponseEnvelope<BannerVO> responseEnv = new ResponseEnvelope<>(bannerVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/banner")
    public ResponseEnvelope<Page<BannerModel>> listBanner(BannerVO bannerVO,Pageable pageable){

		BannerModel param = beanMapper.map(bannerVO, BannerModel.class);
        List<BannerModel> bannerModelModels = bannerService.selectPage(param,pageable);
        long count=bannerService.selectCount(param);
        Page<BannerModel> page = new PageImpl<>(bannerModelModels,pageable,count);
        ResponseEnvelope<Page<BannerModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/banner")
	public ResponseEnvelope<Integer> createBanner(@RequestBody BannerVO bannerVO){
		BannerModel bannerModel = beanMapper.map(bannerVO, BannerModel.class);
		Integer  result = bannerService.createSelective(bannerModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

	/**
	 * 搜索
	 * @param param
	 * @param pageable
     * @return
     */
	@PostMapping(value = "/core/banner/search")
	public ResponseEnvelope<Page<Map<String, Object>>> searchBanner(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String, Object>>  page = bannerService.searchPage(param, pageable);
		ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

    @DeleteMapping(value = "/core/banner/{id}")
	public ResponseEnvelope<Integer> deleteBannerByPrimaryKey(@PathVariable Long id){
		Integer  result = bannerService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/banner/{id}")
	public ResponseEnvelope<Integer> updateBannerByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody BannerVO bannerVO){
		BannerModel bannerModel = beanMapper.map(bannerVO, BannerModel.class);
		bannerModel.setId(id);
		Integer  result = bannerService.updateByPrimaryKeySelective(bannerModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
