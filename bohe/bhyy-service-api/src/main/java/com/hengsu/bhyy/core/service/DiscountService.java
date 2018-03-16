
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DiscountModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscountService {

    int create(DiscountModel discountModel);

    int createSelective(DiscountModel discountModel);

    DiscountModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DiscountModel discountModel);

    int updateByPrimaryKeySelective(DiscountModel discountModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DiscountModel discountModel);

    List<DiscountModel> selectPage(DiscountModel discountModel, Pageable pageable);

}