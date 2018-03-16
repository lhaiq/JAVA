package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.BillItem;
import com.hengsu.bhyy.core.repository.BillItemRepository;
import com.hengsu.bhyy.core.model.BillItemModel;
import com.hengsu.bhyy.core.service.BillItemService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class BillItemServiceImpl implements BillItemService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BillItemRepository billItemRepo;

	@Transactional
	@Override
	public int create(BillItemModel billItemModel) {
		return billItemRepo.insert(beanMapper.map(billItemModel, BillItem.class));
	}

	@Transactional
	@Override
	public int createSelective(BillItemModel billItemModel) {
		return billItemRepo.insertSelective(beanMapper.map(billItemModel, BillItem.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return billItemRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public BillItemModel findByPrimaryKey(Long id) {
		BillItem billItem = billItemRepo.selectByPrimaryKey(id);
		return beanMapper.map(billItem, BillItemModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(BillItemModel billItemModel) {
		return billItemRepo.selectCount(beanMapper.map(billItemModel, BillItem.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<BillItemModel> selectPage(BillItemModel billItemModel,Pageable pageable) {
		BillItem billItem = beanMapper.map(billItemModel, BillItem.class);
		return beanMapper.mapAsList(billItemRepo.selectPage(billItem,pageable),BillItemModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(BillItemModel billItemModel) {
		return billItemRepo.updateByPrimaryKey(beanMapper.map(billItemModel, BillItem.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(BillItemModel billItemModel) {
		return billItemRepo.updateByPrimaryKeySelective(beanMapper.map(billItemModel, BillItem.class));
	}

}
