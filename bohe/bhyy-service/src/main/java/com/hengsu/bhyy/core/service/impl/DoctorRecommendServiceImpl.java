package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.DoctorRecommend;
import com.hengsu.bhyy.core.repository.DoctorRecommendRepository;
import com.hengsu.bhyy.core.model.DoctorRecommendModel;
import com.hengsu.bhyy.core.service.DoctorRecommendService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;
import java.util.Map;

@Service
public class DoctorRecommendServiceImpl implements DoctorRecommendService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorRecommendRepository doctorRecommendRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(DoctorRecommendModel doctorRecommendModel) {
		return doctorRecommendRepo.insert(beanMapper.map(doctorRecommendModel, DoctorRecommend.class));
	}

	@Transactional
	@Override
	public int createSelective(DoctorRecommendModel doctorRecommendModel) {
		return doctorRecommendRepo.insertSelective(beanMapper.map(doctorRecommendModel, DoctorRecommend.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return doctorRecommendRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DoctorRecommendModel findByPrimaryKey(Long id) {
		DoctorRecommend doctorRecommend = doctorRecommendRepo.selectByPrimaryKey(id);
		return beanMapper.map(doctorRecommend, DoctorRecommendModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DoctorRecommendModel doctorRecommendModel) {
		return doctorRecommendRepo.selectCount(beanMapper.map(doctorRecommendModel, DoctorRecommend.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DoctorRecommendModel> selectPage(DoctorRecommendModel doctorRecommendModel,Pageable pageable) {
		DoctorRecommend doctorRecommend = beanMapper.map(doctorRecommendModel, DoctorRecommend.class);
		return beanMapper.mapAsList(doctorRecommendRepo.selectPage(doctorRecommend,pageable),DoctorRecommendModel.class);
	}

	@Override
	public List<Map<String, Object>> selectRecommends(Long presenter) {

		String sql="SELECT dc.id,d.id as doctorId,d.real_name as realName,d.phone,d.hospital_name as hospitalName,\n" +
				"d.title,d.department,d.adept,d.age,d.education\n" +
				"FROM doctor_recommend dc\n" +
				"JOIN doctor d on d.id=dc.presentee\n" +
				"WHERE dc.presenter=? and d.status=1";
		return jdbcTemplate.queryForList(sql,presenter);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DoctorRecommendModel doctorRecommendModel) {
		return doctorRecommendRepo.updateByPrimaryKey(beanMapper.map(doctorRecommendModel, DoctorRecommend.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DoctorRecommendModel doctorRecommendModel) {
		return doctorRecommendRepo.updateByPrimaryKeySelective(beanMapper.map(doctorRecommendModel, DoctorRecommend.class));
	}

}
