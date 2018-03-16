package com.hengsu.bhyy.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.DoctorConfig;
import com.hengsu.bhyy.core.repository.DoctorConfigRepository;
import com.hengsu.bhyy.core.model.DoctorConfigModel;
import com.hengsu.bhyy.core.service.DoctorConfigService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DoctorConfigServiceImpl implements DoctorConfigService {

	private final Logger logger = LoggerFactory.getLogger(DoctorConfigServiceImpl.class);


	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorConfigRepository doctorConfigRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Transactional
	@Override
	public int create(DoctorConfigModel doctorConfigModel) {
		return doctorConfigRepo.insert(beanMapper.map(doctorConfigModel, DoctorConfig.class));
	}

	@Transactional
	@Override
	public int createSelective(DoctorConfigModel doctorConfigModel) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int dayOfWeek = simpleDateFormat.parse(doctorConfigModel.getDate()).getDay();
			doctorConfigModel.setDayOfWeek(dayOfWeek);
		} catch (ParseException e) {
			logger.error("unexpected error", e);
		}


		DoctorConfig doctorConfig = beanMapper.map(doctorConfigModel, DoctorConfig.class);
		int retVal = doctorConfigRepo.insertSelective(doctorConfig);
		doctorConfigModel.setId(doctorConfig.getId());
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return doctorConfigRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DoctorConfigModel findByPrimaryKey(Long id) {
		DoctorConfig doctorConfig = doctorConfigRepo.selectByPrimaryKey(id);
		return beanMapper.map(doctorConfig, DoctorConfigModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DoctorConfigModel doctorConfigModel) {
		return doctorConfigRepo.selectCount(beanMapper.map(doctorConfigModel, DoctorConfig.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DoctorConfigModel> selectPage(DoctorConfigModel doctorConfigModel,Pageable pageable) {
		DoctorConfig doctorConfig = beanMapper.map(doctorConfigModel, DoctorConfig.class);
		return beanMapper.mapAsList(doctorConfigRepo.selectPage(doctorConfig,pageable),DoctorConfigModel.class);
	}

	@Override
	public List<Map<String, Object>> selectConfigByName(String name) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sql="SELECT\n" +
				"  dc.id,\n" +
				"  dc.doctor_id   AS doctorId,\n" +
				"  d.real_name   AS realName,\n" +
				"  d.hospital_name   AS hospitalName,\n" +
				"  dc.hospital_id AS hospitalId,\n" +
				"  dc.date,\n" +
				"  dc.day_of_week AS dayOfWeek,\n" +
				"  dc.`interval`,\n" +
				"  dc.start_time  AS startTime,\n" +
				"  dc.end_time    AS endTime,\n" +
				"  dc.status\n" +
				"FROM doctor_config dc, doctor d\n" +
				"WHERE dc.doctor_id = d.id AND d.real_name LIKE '%"+name+"%' AND dc.date >=? ";
		return jdbcTemplate.queryForList(sql,simpleDateFormat.format(new Date()));
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DoctorConfigModel doctorConfigModel) {
		return doctorConfigRepo.updateByPrimaryKey(beanMapper.map(doctorConfigModel, DoctorConfig.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DoctorConfigModel doctorConfigModel) {
		return doctorConfigRepo.updateByPrimaryKeySelective(beanMapper.map(doctorConfigModel, DoctorConfig.class));
	}

}
