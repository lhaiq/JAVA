package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Discount;
import com.hengsu.bhyy.core.repository.DiscountRepository;
import com.hengsu.bhyy.core.model.DiscountModel;
import com.hengsu.bhyy.core.service.DiscountService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscountRepository discountRepo;

	@Transactional
	@Override
	public int create(DiscountModel discountModel) {
		return discountRepo.insert(beanMapper.map(discountModel, Discount.class));
	}

	@Transactional
	@Override
	public int createSelective(DiscountModel discountModel) {
		discountModel.setCreateTime(new Date());
		return discountRepo.insertSelective(beanMapper.map(discountModel, Discount.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return discountRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DiscountModel findByPrimaryKey(Long id) {
		Discount discount = discountRepo.selectByPrimaryKey(id);
		return beanMapper.map(discount, DiscountModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DiscountModel discountModel) {
		return discountRepo.selectCount(beanMapper.map(discountModel, Discount.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DiscountModel> selectPage(DiscountModel discountModel,Pageable pageable) {
		Discount discount = beanMapper.map(discountModel, Discount.class);
		return beanMapper.mapAsList(discountRepo.selectPage(discount,pageable),DiscountModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DiscountModel discountModel) {
		return discountRepo.updateByPrimaryKey(beanMapper.map(discountModel, Discount.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DiscountModel discountModel) {
		return discountRepo.updateByPrimaryKeySelective(beanMapper.map(discountModel, Discount.class));
	}

}
