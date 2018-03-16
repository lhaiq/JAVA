package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Item;
import com.hengsu.bhyy.core.repository.ItemRepository;
import com.hengsu.bhyy.core.model.ItemModel;
import com.hengsu.bhyy.core.service.ItemService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ItemRepository itemRepo;

	@Transactional
	@Override
	public int create(ItemModel itemModel) {
		return itemRepo.insert(beanMapper.map(itemModel, Item.class));
	}

	@Transactional
	@Override
	public int createSelective(ItemModel itemModel) {
		return itemRepo.insertSelective(beanMapper.map(itemModel, Item.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return itemRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ItemModel findByPrimaryKey(Long id) {
		Item item = itemRepo.selectByPrimaryKey(id);
		return beanMapper.map(item, ItemModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(ItemModel itemModel) {
		return itemRepo.selectCount(beanMapper.map(itemModel, Item.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ItemModel> selectPage(ItemModel itemModel,Pageable pageable) {
		Item item = beanMapper.map(itemModel, Item.class);
		return beanMapper.mapAsList(itemRepo.selectPage(item,pageable),ItemModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(ItemModel itemModel) {
		return itemRepo.updateByPrimaryKey(beanMapper.map(itemModel, Item.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(ItemModel itemModel) {
		return itemRepo.updateByPrimaryKeySelective(beanMapper.map(itemModel, Item.class));
	}

}
