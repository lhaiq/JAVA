package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.entity.Bill;
import com.hengsu.bhyy.core.model.AppointmentModel;
import com.hengsu.bhyy.core.model.BillItemModel;
import com.hengsu.bhyy.core.model.BillModel;
import com.hengsu.bhyy.core.model.ItemModel;
import com.hengsu.bhyy.core.repository.BillRepository;
import com.hengsu.bhyy.core.service.*;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private BillItemService billItemService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DoctorService doctorService;

	@Transactional
	@Override
	public int create(BillModel billModel) {
		return billRepo.insert(beanMapper.map(billModel, Bill.class));
	}

	@Transactional
	@Override
	public int createSelective(BillModel billModel) {

		AppointmentModel appointmentModel = appointmentService.findByPrimaryKey(billModel.getAppointmentId());


		billModel.setDoctorId(appointmentModel.getDoctorId());
		billModel.setDoctorName(appointmentModel.getDoctorName());
		billModel.setCustomerId(appointmentModel.getCustomerId());
		billModel.setPatientName(appointmentModel.getPatientName());
		billModel.setHospitalName(appointmentModel.getHospitalName());
		billModel.setCreateTime(new Date());
		billModel.setAddress(appointmentModel.getAddress());
		billModel.setBillId(RandomUtil.getIdByUUIdAndTime());
		Bill bill = beanMapper.map(billModel, Bill.class);
		int retVal = billRepo.insertSelective(bill);
		for(BillItemModel billItemModel:billModel.getItems()){
			billItemModel.setBillId(bill.getId());
			ItemModel itemModel = itemService.findByPrimaryKey(billItemModel.getItemId());
			billItemModel.setItemName(itemModel.getName());
			billItemModel.setOriginalPrice(itemModel.getPrice());
			billItemModel.setActualCost(itemModel.getPrice().multiply(new BigDecimal(billItemModel.getNum()))
					.multiply(billItemModel.getDiscount()));
			billItemService.createSelective(billItemModel);
		}
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return billRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public BillModel findByPrimaryKey(Long id) {
		Bill bill = billRepo.selectByPrimaryKey(id);
		return beanMapper.map(bill, BillModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(BillModel billModel) {
		return billRepo.selectCount(beanMapper.map(billModel, Bill.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<BillModel> selectPage(BillModel billModel,Pageable pageable) {
		Bill bill = beanMapper.map(billModel, Bill.class);
		return beanMapper.mapAsList(billRepo.selectPage(bill,pageable),BillModel.class);
	}

	@Override
	public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
		String select = "SELECT\n" +
				"  b.id,\n" +
				"  b.bill_id        AS billId,\n" +
				"  b.appointment_id AS appointmentId,\n" +
				"  b.customer_id    AS customerId,\n" +
				"  c.phone,\n" +
				"  b.patient_name   AS patientName,\n" +
				"  b.doctor_id      AS doctorId,\n" +
				"  b.doctor_name    AS doctorName,\n" +
				"  b.item_name      AS itemName,\n" +
				"  b.create_time    AS createTime,\n" +
				"  b.original_cost  AS originalCost,\n" +
				"  b.discount,\n" +
				"  b.actual_cost    AS actualCost,\n" +
				"  b.hospital_name  AS hospitalName,\n" +
				"  b.coupon_amount  AS couponAmount,\n" +
				"  b.is_on_sale     AS isOnSale,\n" +
				"  b.pay_type       AS payType,\n" +
				"  b.is_recheck     AS isRecheck,\n" +
				"  b.recheck_date   AS recheckDate,\n" +
				"  b.remark,\n" +
				"  b.is_comment     AS isComment,\n" +
				"  b.status ";

		String tables = " FROM appointment a,bill b,customer c ";

		StringBuffer condition = new StringBuffer();

		condition.append(" WHERE 1=1 and  a.id = b.appointment_id and a.patient_id = c.id ");

		if (StringUtils.isNotEmpty(param.get("billId"))) {
			condition.append(" and b.bill_id='" + param.get("billId") + "'");
		}

		if (StringUtils.isNotEmpty(param.get("phone"))) {
			condition.append("and c.phone='" + param.get("phone") + "'");
		}

		if (StringUtils.isNotEmpty(param.get("status"))) {
			condition.append(" and b.status=" + param.get("status"));
		}

		if (StringUtils.isNotEmpty(param.get("payType"))) {
			condition.append(" and b.pay_type=" + param.get("payType"));
		}

		if (StringUtils.isNotEmpty(param.get("isOnSale"))) {
			condition.append(" and b.is_on_sale=" + param.get("isOnSale"));
		}

		if (StringUtils.isNotEmpty(param.get("startTime"))) {
			condition.append(" and b.create_time > '" + param.get("startTime")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("endTime"))) {
			condition.append(" and b.create_time < '" + param.get("endTime")+"'");
		}

		if (StringUtils.isNotEmpty(param.get("itemName"))) {
			condition.append(" and b.item_name like '%" + param.get("itemName") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("patientName"))) {
			condition.append(" and b.patient_name like '%" + param.get("patientName") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("doctorName"))) {
			condition.append(" and b.doctor_name LIKE '%" + param.get("doctorName") + "%'");
		}

		if (StringUtils.isNotEmpty(param.get("hospitalName"))) {
			condition.append(" and b.hospital_name LIKE '%" + param.get("hospitalName") + "%'");
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

	@Transactional
	@Override
	public void pay(BillModel billModel,int payType) {

		BillModel param = new BillModel();
		param.setId(billModel.getId());
		param.setStatus(1);
		param.setPayType(payType);
		updateByPrimaryKey(param);
		doctorService.addBalance(billModel.getDoctorId(),billModel.getId(),billModel.getActualCost());


	}

	@Transactional
	@Override
	public int updateByPrimaryKey(BillModel billModel) {
		return billRepo.updateByPrimaryKey(beanMapper.map(billModel, Bill.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(BillModel billModel) {
		return billRepo.updateByPrimaryKeySelective(beanMapper.map(billModel, Bill.class));
	}

}
