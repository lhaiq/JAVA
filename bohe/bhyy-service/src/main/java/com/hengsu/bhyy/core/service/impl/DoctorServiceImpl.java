package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.model.WalletModel;
import com.hengsu.bhyy.core.service.BillService;
import com.hengsu.bhyy.core.service.CustomerService;
import com.hengsu.bhyy.core.service.WalletService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Doctor;
import com.hengsu.bhyy.core.repository.DoctorRepository;
import com.hengsu.bhyy.core.model.DoctorModel;
import com.hengsu.bhyy.core.service.DoctorService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DoctorRepository doctorRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BillService billService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private CustomerService customerService;

	@Transactional
	@Override
	public int create(DoctorModel doctorModel) {
		return doctorRepo.insert(beanMapper.map(doctorModel, Doctor.class));
	}

	@Transactional
	@Override
	public int createSelective(DoctorModel doctorModel) {
		Doctor doctor = beanMapper.map(doctorModel, Doctor.class);
		int retVal = doctorRepo.insertSelective(doctor);
		doctorModel.setId(doctor.getId());
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return doctorRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DoctorModel findByPrimaryKey(Long id) {
		Doctor doctor = doctorRepo.selectByPrimaryKey(id);
		return beanMapper.map(doctor, DoctorModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DoctorModel doctorModel) {
		return doctorRepo.selectCount(beanMapper.map(doctorModel, Doctor.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DoctorModel> selectPage(DoctorModel doctorModel,Pageable pageable) {
		Doctor doctor = beanMapper.map(doctorModel, Doctor.class);
		return beanMapper.mapAsList(doctorRepo.selectPage(doctor,pageable),DoctorModel.class);
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
		String select = "  SELECT d.id,\n" +
				"  d.real_name     AS realName,\n" +
				"  d.phone,\n" +
				"  d.hospital_name AS hospitalName,\n" +
				"  d.source,\n" +
				"  d.status,\n" +
				"  d.rank,\n" +
				"  d.add_date      AS addDate,\n" +
				"  d.is_show       AS isShow,\n" +
				"  d.icon,\n" +
				"  d.education,\n" +
				"  d.is_recommend  AS isRecommend ";
		String tables = " FROM doctor d  ";

		StringBuffer condition = new StringBuffer();
		condition.append(" WHERE 1=1 ");

		if (StringUtils.isNotEmpty(param.get("id"))) {
			condition.append(" and d.id="+ param.get("id"));
		}

		if (StringUtils.isNotEmpty(param.get("phone"))) {
			condition.append(" and d.phone="+ param.get("phone"));
		}

		if (StringUtils.isNotEmpty(param.get("invite_code"))) {
			condition.append(" and d.invite_code="+ param.get("inviteCode"));
		}

		if (StringUtils.isNotEmpty(param.get("source"))) {
			condition.append(" and d.source="+ param.get("source"));
		}

		if (StringUtils.isNotEmpty(param.get("status"))) {
			condition.append(" and d.status="+ param.get("status"));
		}

		if (StringUtils.isNotEmpty(param.get("startDate"))) {
			condition.append(" and d.add_date>='"+ param.get("startDate")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("endDate"))) {
			condition.append(" and d.add_date<='"+ param.get("endDate")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("name"))) {
			condition.append(" and d.real_name like '%" + param.get("name") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("hospitalName"))) {
			condition.append(" and d.hospital_name like '%" + param.get("hospitalName") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("adept"))) {
			condition.append(" and d.adept like '%" + param.get("adept") + "%'");
		}

		StringBuffer limitSql = new StringBuffer();
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
		}

		List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
		Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);
		Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

		return page;

	}

	@Override
	public Page<Map<String, Object>> searchPageForApp(Map<String, String> param, Pageable pageable) {

		String select = "SELECT d.id,d.real_name as realName,d.hospital_name as hospitalName,d.icon,d.education,d.is_recommend as isRecommend ";

		String tables = " FROM doctor d  ";

		StringBuffer condition = new StringBuffer();


		condition.append(" WHERE 1=1 ");


		condition.append(" and d.id in (\n" +
				"  SELECT doctor_id\n" +
				"  FROM doctor_config \n");

		if (StringUtils.isNotEmpty(param.get("dayOfWeek"))) {
			condition.append(" and day_of_week="+ param.get("dayOfWeek"));
		}

		condition.append(")");

		if (StringUtils.isNotEmpty(param.get("name"))) {
			condition.append(" and d.real_name like '%" + param.get("name") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("itemName"))) {
			condition.append(" and d.department like '%" + param.get("itemName") + "%'");
		}

		condition.append("and d.status =1 ");





		StringBuffer limitSql = new StringBuffer();
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
		}


		List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
		Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);

		Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

		return page;

	}

	@Transactional
	@Override
	public void addBalance(Long id, Long billId,BigDecimal money) {
		WalletModel walletModel = new WalletModel();
		walletModel.setCreateTime(new Date());
		walletModel.setDoctorId(id);
		walletModel.setBillId(billId);
		walletModel.setName(billService.findByPrimaryKey(billId).getItemName());
		walletModel.setMoney(money);
		walletService.createSelective(walletModel);

		//更新医生
		String doctorSql = "update doctor set balance = balance + ? where id = ?";
		jdbcTemplate.update(doctorSql,money,id);

		//更新客户
		String customerSql = "update customer set pay_money = pay_money + ? where id = ?";
		jdbcTemplate.update(customerSql,money,id);


	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DoctorModel doctorModel) {
		return doctorRepo.updateByPrimaryKey(beanMapper.map(doctorModel, Doctor.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DoctorModel doctorModel) {
		return doctorRepo.updateByPrimaryKeySelective(beanMapper.map(doctorModel, Doctor.class));
	}

}
