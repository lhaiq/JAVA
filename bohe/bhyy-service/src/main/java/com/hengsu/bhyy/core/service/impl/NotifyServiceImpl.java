package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Notify;
import com.hengsu.bhyy.core.repository.NotifyRepository;
import com.hengsu.bhyy.core.model.NotifyModel;
import com.hengsu.bhyy.core.service.NotifyService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.web.servlet.tags.form.SelectTag;

import java.util.*;

@Service
public class NotifyServiceImpl implements NotifyService {


	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private NotifyRepository notifyRepo;

	@Transactional
	@Override
	public int create(NotifyModel notifyModel) {
		return notifyRepo.insert(beanMapper.map(notifyModel, Notify.class));
	}

	@Transactional
	@Override
	public int createSelective(NotifyModel notifyModel) {
		return notifyRepo.insertSelective(beanMapper.map(notifyModel, Notify.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return notifyRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public NotifyModel findByPrimaryKey(Long id) {
		Notify notify = notifyRepo.selectByPrimaryKey(id);
		return beanMapper.map(notify, NotifyModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(NotifyModel notifyModel) {
		return notifyRepo.selectCount(beanMapper.map(notifyModel, Notify.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<NotifyModel> selectPage(NotifyModel notifyModel,Pageable pageable) {
		Notify notify = beanMapper.map(notifyModel, Notify.class);
		return beanMapper.mapAsList(notifyRepo.selectPage(notify,pageable),NotifyModel.class);
	}

	@Transactional
	@Override
	public List<NotifyModel> selectByCustomerId(Long customerId) {

		Set<Integer> notifySets = new HashSet<>();
		notifySets.addAll(Arrays.asList(0,1,2,3,4,5,6));
		NotifyModel param = new NotifyModel();
		List<NotifyModel> notifyModels = selectPage(param,new PageRequest(0,Integer.MAX_VALUE));
		for(NotifyModel notifyModel:notifyModels){
			notifySets.remove(notifyModel.getType());
		}

		for (Integer type:notifySets){
			NotifyModel notifyModel = new NotifyModel();
			notifyModel.setCustomerId(customerId);
			notifyModel.setType(type);
			notifyModel.setStatus(0);
			notifyModel.setUpdateTime(new Date());
			createSelective(notifyModel);
		}

		return selectPage(param,new PageRequest(0,Integer.MAX_VALUE));
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(NotifyModel notifyModel) {
		return notifyRepo.updateByPrimaryKey(beanMapper.map(notifyModel, Notify.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(NotifyModel notifyModel) {
		return notifyRepo.updateByPrimaryKeySelective(beanMapper.map(notifyModel, Notify.class));
	}

}
