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

import com.hengsu.bhyy.core.service.HospitalService;
import com.hengsu.bhyy.core.model.HospitalModel;
import com.hengsu.bhyy.core.vo.HospitalVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class HospitalRestApiController {

    private final Logger logger = LoggerFactory.getLogger(HospitalRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private HospitalService hospitalService;

    @GetMapping(value = "/core/hospital/{id}")
    public ResponseEnvelope<HospitalVO> getHospitalById(@PathVariable Long id) {
        HospitalModel hospitalModel = hospitalService.findByPrimaryKey(id);
        HospitalVO hospitalVO = beanMapper.map(hospitalModel, HospitalVO.class);
        ResponseEnvelope<HospitalVO> responseEnv = new ResponseEnvelope<>(hospitalVO, true);
        return responseEnv;
    }

    @PostMapping(value = "/core/hospital/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchHospital(@RequestBody Map<String, String> param, Pageable pageable) {
        Page<Map<String, Object>> page = hospitalService.searchPage(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    @PostMapping(value = "/core/hospital")
    public ResponseEnvelope<Integer> createHospital(@RequestBody HospitalVO hospitalVO) {
        HospitalModel hospitalModel = beanMapper.map(hospitalVO, HospitalModel.class);
        hospitalModel.setCreateTime(new Date());
        Integer result = hospitalService.createSelective(hospitalModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }

    @DeleteMapping(value = "/core/hospital/{id}")
    public ResponseEnvelope<Integer> deleteHospitalByPrimaryKey(@PathVariable Long id) {
        Integer result = hospitalService.deleteByPrimaryKey(id);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }


    @PutMapping(value = "/core/hospital/{id}")
    public ResponseEnvelope<Integer> updateHospitalByPrimaryKeySelective(@PathVariable Long id,
                                                                         @RequestBody HospitalVO hospitalVO) {
        HospitalModel hospitalModel = beanMapper.map(hospitalVO, HospitalModel.class);
        hospitalModel.setId(id);
        Integer result = hospitalService.updateByPrimaryKeySelective(hospitalModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }

}
