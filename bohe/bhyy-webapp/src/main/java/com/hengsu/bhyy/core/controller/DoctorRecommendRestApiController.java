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

import com.hengsu.bhyy.core.service.DoctorRecommendService;
import com.hengsu.bhyy.core.model.DoctorRecommendModel;
import com.hengsu.bhyy.core.vo.DoctorRecommendVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class DoctorRecommendRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DoctorRecommendRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorRecommendService doctorRecommendService;

	@GetMapping(value = "/core/doctorRecommend/{id}")
	public ResponseEnvelope<DoctorRecommendVO> getDoctorRecommendById(@PathVariable Long id){
		DoctorRecommendModel doctorRecommendModel = doctorRecommendService.findByPrimaryKey(id);
		DoctorRecommendVO doctorRecommendVO =beanMapper.map(doctorRecommendModel, DoctorRecommendVO.class);
		ResponseEnvelope<DoctorRecommendVO> responseEnv = new ResponseEnvelope<>(doctorRecommendVO,true);
		return responseEnv;
	}

	/**
	 * 查询指定医生的推荐医生
	 * @param doctorRecommendVO
     * @return
     */
	@GetMapping(value = "/core/doctorRecommend")
    public ResponseEnvelope<List<Map<String, Object>>> listDoctorRecommend(DoctorRecommendVO doctorRecommendVO){
		List<Map<String, Object>> maps = doctorRecommendService.selectRecommends(doctorRecommendVO.getPresenter());
        ResponseEnvelope<List<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(maps,true);
        return responseEnv;
    }


	@PostMapping(value = "/core/doctorRecommend")
	public ResponseEnvelope<Integer> createDoctorRecommend(@RequestBody DoctorRecommendVO doctorRecommendVO){
		DoctorRecommendModel doctorRecommendModel = beanMapper.map(doctorRecommendVO, DoctorRecommendModel.class);
		doctorRecommendModel.setUpdateTime(new Date());
		Integer  result = doctorRecommendService.createSelective(doctorRecommendModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/doctorRecommend/{id}")
	public ResponseEnvelope<Integer> deleteDoctorRecommendByPrimaryKey(@PathVariable Long id){
		Integer  result = doctorRecommendService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/doctorRecommend/{id}")
	public ResponseEnvelope<Integer> updateDoctorRecommendByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody DoctorRecommendVO doctorRecommendVO){
		DoctorRecommendModel doctorRecommendModel = beanMapper.map(doctorRecommendVO, DoctorRecommendModel.class);
		doctorRecommendModel.setId(id);
		doctorRecommendModel.setUpdateTime(new Date());
		Integer  result = doctorRecommendService.updateByPrimaryKeySelective(doctorRecommendModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
