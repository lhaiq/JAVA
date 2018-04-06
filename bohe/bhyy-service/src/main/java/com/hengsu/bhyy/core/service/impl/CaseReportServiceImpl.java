package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CaseReport;
import com.hengsu.bhyy.core.repository.CaseReportRepository;
import com.hengsu.bhyy.core.model.CaseReportModel;
import com.hengsu.bhyy.core.service.CaseReportService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;
import java.util.Map;

@Service
public class CaseReportServiceImpl implements CaseReportService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CaseReportRepository caseReportRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(CaseReportModel caseReportModel) {
		return caseReportRepo.insert(beanMapper.map(caseReportModel, CaseReport.class));
	}

	@Transactional
	@Override
	public int createSelective(CaseReportModel caseReportModel) {
		return caseReportRepo.insertSelective(beanMapper.map(caseReportModel, CaseReport.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return caseReportRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CaseReportModel findByPrimaryKey(Long id) {
		CaseReport caseReport = caseReportRepo.selectByPrimaryKey(id);
		return beanMapper.map(caseReport, CaseReportModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CaseReportModel caseReportModel) {
		return caseReportRepo.selectCount(beanMapper.map(caseReportModel, CaseReport.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CaseReportModel> selectPage(CaseReportModel caseReportModel,Pageable pageable) {
		CaseReport caseReport = beanMapper.map(caseReportModel, CaseReport.class);
		return beanMapper.mapAsList(caseReportRepo.selectPage(caseReport,pageable),CaseReportModel.class);
	}

	@Override
	public Page<Map<String, Object>> primaryScreenSearch(Map<String, Object> param, Pageable pageable) {
		String select = "  SELECT cr.id,c.real_name as customerName,c.gender,c.phone,h.name AS hospitalName,d.real_name as doctorName,cr.is_send as isSend ";
		String tables = " FROM case_report cr,customer c,hospital h,doctor d   ";

		StringBuffer condition = new StringBuffer();
		condition.append(" where 1=1 and cr.customer_id=c.id and cr.hospital_id=h.id and cr.doctor_id=d.id  ");

		condition.append(" and cr.case_type=0 ");

		if (param.containsKey("id")) {
			condition.append(" and cr.id like '%" + param.get("id") + "%' ");
		}

		if (param.containsKey("name")) {
			condition.append(" and c.real_name like '%" + param.get("name") + "%'");
		}

		if (param.containsKey("phone")) {
			condition.append(" and c.phone like '%" + param.get("phone") + "%'");
		}

		if (param.containsKey("gender")) {
			condition.append(" and c.gender=" + param.get("gender"));
		}

		if (param.containsKey("startDate")) {
			condition.append(" and cr.time>='" + param.get("startDate") + "'");
		}

		if (param.containsKey("endDate")) {
			condition.append(" and cr.time<='" + param.get("endDate") + "'");
		}

		if (param.containsKey("hospitalName")) {
			condition.append(" and h.hospital_name like '%" + param.get("hospitalName") + "%'");
		}


		StringBuffer limitSql = new StringBuffer();
		limitSql.append("order by cr.time desc");
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
		}

		List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
		Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);
		Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

		return page;
	}

	@Override
	public Page<Map<String, Object>> searchCaseReport(Map<String, Object> param, Pageable pageable) {
		String select = "  SELECT cr.id,cr.type,cr.time,c.real_name as customerName,c.gender,c.phone,d.real_name as doctorName,cr.is_send as isSend,b.item_name as itemName ";
		String tables = " FROM case_report cr,customer c,bill b ,doctor d  ";

		StringBuffer condition = new StringBuffer();
		condition.append(" where 1=1 and cr.customer_id=c.id and cr.doctor_id = d.id and cr.bill_id=b.id  ");

		condition.append(" and cr.case_type!=0 ");

		if (param.containsKey("id")) {
			condition.append(" and cr.id like '%" + param.get("id") + "%' ");
		}

		if (param.containsKey("name")) {
			condition.append(" and c.name like '%" + param.get("name") + "%'");
		}

		if (param.containsKey("phone")) {
			condition.append(" and c.phone like '%" + param.get("phone") + "%'");
		}

		if (param.containsKey("gender")) {
			condition.append(" and c.gender=" + param.get("gender"));
		}

		if (param.containsKey("isSend")) {
			condition.append(" and cr.is_send=" + param.get("isSend"));
		}

		if (param.containsKey("type")) {
			condition.append(" and c.gender=" + param.get("gender"));
		}

		if (param.containsKey("startDate")) {
			condition.append(" and cr.time>='" + param.get("startDate") + "'");
		}

		if (param.containsKey("endDate")) {
			condition.append(" and cr.time<='" + param.get("endDate") + "'");
		}


		if (param.containsKey("itemName")) {
			condition.append(" and b.item_name like '%" + param.get("itemName") + "%'");
		}


		StringBuffer limitSql = new StringBuffer();
		limitSql.append("order by cr.create_time desc");
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
		}

		List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
		Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);
		Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

		return page;
	}

	@Override
	public Page<Map<String, Object>> searchDoctorCaseReport(Long doctorId, Map<String, Object> param, Pageable pageable) {
		String select = "  SELECT cr.id,c.real_name as customerName,c.gender,c.phone, b.item_name AS itemName ";
		String tables = " FROM case_report cr,customer c,bill b   ";

		StringBuffer condition = new StringBuffer();
		condition.append(" where 1=1 and cr.customer_id=c.id and cr.bill_id=b.id  ");

		condition.append(" and cr.doctor_id = "+doctorId);

		if (param.containsKey("type")) {
			condition.append(" and cr.type = " + param.get("type"));
		}

		if (param.containsKey("keywords")) {
			condition.append(" and (");
			condition.append(" c.real_name like '%" + param.get("keywords") + "%'");
			condition.append(" or c.phone like '%" + param.get("keywords") + "%'");
			condition.append(" or b.item_name like '%" + param.get("keywords") + "%'");
			condition.append(")");
		}


		StringBuffer limitSql = new StringBuffer();
		limitSql.append(" order by cr.create_time desc");
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
		}

		List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
		Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);
		Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

		return page;
	}

	@Override
	public Page<Map<String, Object>> searchCustomerCaseReport(Long customerId, Map<String, Object> param, Pageable pageable) {
		String select = "  SELECT cr.id,c.real_name as customerName,c.gender,c.phone, b.item_name AS itemName ";
		String tables = " FROM case_report cr,customer c,bill b   ";

		StringBuffer condition = new StringBuffer();
		condition.append(" where 1=1 and cr.customer_id=c.id and cr.bill_id=b.id  ");

		condition.append(" and cr.customer_id = "+customerId);

		StringBuffer limitSql = new StringBuffer();
		limitSql.append(" order by cr.create_time desc");
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
	public int updateByPrimaryKey(CaseReportModel caseReportModel) {
		return caseReportRepo.updateByPrimaryKey(beanMapper.map(caseReportModel, CaseReport.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CaseReportModel caseReportModel) {
		return caseReportRepo.updateByPrimaryKeySelective(beanMapper.map(caseReportModel, CaseReport.class));
	}

}
