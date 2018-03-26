
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.BillModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BillService {

    int create(BillModel billModel);

    int createSelective(BillModel billModel);

    BillModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(BillModel billModel);

    int updateByPrimaryKeySelective(BillModel billModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(BillModel billModel);

    List<BillModel> selectPage(BillModel billModel, Pageable pageable);

    Page<Map<String, Object>> selectPageByKeyWords(Long doctorId,String keyWords, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable);

    void pay(BillModel billModel,int payType);

}