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

import com.hengsu.bhyy.core.service.WalletService;
import com.hengsu.bhyy.core.model.WalletModel;
import com.hengsu.bhyy.core.vo.WalletVO;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class WalletRestApiController {

	private final Logger logger = LoggerFactory.getLogger(WalletRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private WalletService walletService;

	@GetMapping(value = "/core/wallet/{id}")
	public ResponseEnvelope<WalletVO> getWalletById(@PathVariable Long id){
		WalletModel walletModel = walletService.findByPrimaryKey(id);
		WalletVO walletVO =beanMapper.map(walletModel, WalletVO.class);
		ResponseEnvelope<WalletVO> responseEnv = new ResponseEnvelope<>(walletVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/wallet")
    public ResponseEnvelope<Page<WalletModel>> listWallet(@RequestAttribute("userId") Long doctorId,
														  Pageable pageable){

		WalletModel param = new WalletModel();
		param.setDoctorId(doctorId);
        List<WalletModel> walletModelModels = walletService.selectPage(param,pageable);
        long count=walletService.selectCount(param);
        Page<WalletModel> page = new PageImpl<>(walletModelModels,pageable,count);
        ResponseEnvelope<Page<WalletModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }


}
