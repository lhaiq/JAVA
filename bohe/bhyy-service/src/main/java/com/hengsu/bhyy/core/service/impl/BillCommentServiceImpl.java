package com.hengsu.bhyy.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.hengsu.bhyy.core.model.BillModel;
import com.hengsu.bhyy.core.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.BillComment;
import com.hengsu.bhyy.core.repository.BillCommentRepository;
import com.hengsu.bhyy.core.model.BillCommentModel;
import com.hengsu.bhyy.core.service.BillCommentService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.Date;
import java.util.List;

@Service
public class BillCommentServiceImpl implements BillCommentService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BillCommentRepository billCommentRepo;

	@Autowired
	private BillService billService;

	@Transactional
	@Override
	public int create(BillCommentModel billCommentModel) {
		return billCommentRepo.insert(beanMapper.map(billCommentModel, BillComment.class));
	}

	@Transactional
	@Override
	public int createSelective(BillCommentModel billCommentModel) {
		billCommentModel.setCreateTime(new Date());
		billCommentModel.setTags(JSON.toJSONString(billCommentModel.getTagIds()));
		BillModel billModel = new BillModel();
		billModel.setId(billCommentModel.getBillId());
		billModel.setIsComment(1);
		billService.updateByPrimaryKeySelective(billModel);
		return billCommentRepo.insertSelective(beanMapper.map(billCommentModel, BillComment.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return billCommentRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public BillCommentModel findByPrimaryKey(Long id) {
		BillComment billComment = billCommentRepo.selectByPrimaryKey(id);
		return beanMapper.map(billComment, BillCommentModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(BillCommentModel billCommentModel) {
		return billCommentRepo.selectCount(beanMapper.map(billCommentModel, BillComment.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<BillCommentModel> selectPage(BillCommentModel billCommentModel,Pageable pageable) {
		BillComment billComment = beanMapper.map(billCommentModel, BillComment.class);
		return beanMapper.mapAsList(billCommentRepo.selectPage(billComment,pageable),BillCommentModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(BillCommentModel billCommentModel) {
		return billCommentRepo.updateByPrimaryKey(beanMapper.map(billCommentModel, BillComment.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(BillCommentModel billCommentModel) {
		return billCommentRepo.updateByPrimaryKeySelective(beanMapper.map(billCommentModel, BillComment.class));
	}

}
