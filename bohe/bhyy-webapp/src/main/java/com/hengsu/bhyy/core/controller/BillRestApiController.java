package com.hengsu.bhyy.core.controller;

import com.alibaba.fastjson.JSON;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.model.BillCommentModel;
import com.hengsu.bhyy.core.model.BillItemModel;
import com.hengsu.bhyy.core.service.BillCommentService;
import com.hengsu.bhyy.core.service.BillItemService;
import com.hengsu.bhyy.core.vo.BillCommentVO;
import org.apache.commons.collections4.CollectionUtils;
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

import com.hengsu.bhyy.core.service.BillService;
import com.hengsu.bhyy.core.model.BillModel;
import com.hengsu.bhyy.core.vo.BillVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class BillRestApiController {

    private final Logger logger = LoggerFactory.getLogger(BillRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private BillService billService;

    @Autowired
    private BillItemService billItemService;

    @Autowired
    private BillCommentService billCommentService;

    @GetMapping(value = "/core/bill/{id}")
    public ResponseEnvelope<BillVO> getBillById(@PathVariable Long id) {
        BillModel billModel = billService.findByPrimaryKey(id);
        BillItemModel param = new BillItemModel();
        param.setBillId(billModel.getId());
        List<BillItemModel> items = billItemService.selectPage(param, new PageRequest(0, Integer.MAX_VALUE));
        billModel.setItems(items);
        BillVO billVO = beanMapper.map(billModel, BillVO.class);
        if (billModel.getIsComment() == 1) {
            BillCommentModel commentParam = new BillCommentModel();
            param.setBillId(id);
            List<BillCommentModel> billCommentModels = billCommentService.selectPage(commentParam, new PageRequest(0, 1));
            BillCommentModel billCommentModel = null;
            if (CollectionUtils.isEmpty(billCommentModels)) {
                billCommentModel = new BillCommentModel();
            } else {
                billCommentModel = billCommentModels.get(0);
                billCommentModel.setTagIds(JSON.parseArray(billCommentModel.getTags(), Long.class));
            }

            BillCommentVO billCommentVO = beanMapper.map(billCommentModel, BillCommentVO.class);
            billVO.setComment(billCommentVO);
        }
        ResponseEnvelope<BillVO> responseEnv = new ResponseEnvelope<>(billVO, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/customer/bill")
    public ResponseEnvelope<Page<BillModel>> listCustomerBill(@RequestAttribute("userId") Long customerId,
                                                      Pageable pageable) {

        BillModel param = new BillModel();
        param.setCustomerId(customerId);
        List<BillModel> billModelModels = billService.selectPage(param, pageable);
        long count = billService.selectCount(param);
        Page<BillModel> page = new PageImpl<>(billModelModels, pageable, count);
        ResponseEnvelope<Page<BillModel>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    @GetMapping(value = "/core/doctor/bill")
    public ResponseEnvelope<Page<BillModel>> listDoctorBill(@RequestAttribute("userId") Long customerId,
                                                      Pageable pageable) {
        BillModel param = new BillModel();
        param.setDoctorId(customerId);
        List<BillModel> billModelModels = billService.selectPage(param, pageable);
        long count = billService.selectCount(param);
        Page<BillModel> page = new PageImpl<>(billModelModels, pageable, count);
        ResponseEnvelope<Page<BillModel>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }

    /**
     * 生成账单
     *
     * @param billVO
     * @return
     */
    @PostMapping(value = "/core/bill")
    public ResponseEnvelope<Integer> createBill(@RequestBody BillVO billVO) {
        BillModel billModel = beanMapper.map(billVO, BillModel.class);

        Integer result = billService.createSelective(billModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }

    /**
     * 账单搜索-后台
     *
     * @param param
     * @param pageable
     * @return
     */
    @PostMapping(value = "/core/bill/search")
    public ResponseEnvelope<Page<Map<String, Object>>> searchBill(@RequestBody Map<String, String> param, Pageable pageable) {
        Page<Map<String, Object>> page = billService.searchPage(param, pageable);
        ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page, true);
        return responseEnv;
    }


    @DeleteMapping(value = "/core/bill/{id}")
    public ResponseEnvelope<Integer> deleteBillByPrimaryKey(@PathVariable Long id) {
        Integer result = billService.deleteByPrimaryKey(id);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
        return responseEnv;
    }


    @PutMapping(value = "/core/bill/{id}")
    public ResponseEnvelope<Integer> updateBillByPrimaryKeySelective(@PathVariable Long id,
                                                                     @RequestBody BillVO billVO) {
        BillModel billModel = beanMapper.map(billVO, BillModel.class);
        billModel.setId(id);
        Integer result = billService.updateByPrimaryKeySelective(billModel);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result, true);
        return responseEnv;
    }


    @PutMapping(value = "/core/bill/pay/{id}")
    public ResponseEnvelope<Integer> pay(@PathVariable Long id,
                                         @RequestAttribute("userId") Long userId
                                                  ) {
        BillModel billModel = billService.findByPrimaryKey(id);
        if(billModel.getStatus()!=0){
            HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("5000","该订单已支付"));
        }

        if(!billModel.getCustomerId().equals(userId)){
            HRErrorCode.throwBusinessException(HRErrorCode.ErrorCode("5000","只能支付自己的订单"));
        }

        billService.pay(billModel,1);
        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(1, true);
        return responseEnv;
    }

}
