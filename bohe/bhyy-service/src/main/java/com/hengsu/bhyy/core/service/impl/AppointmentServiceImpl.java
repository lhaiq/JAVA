package com.hengsu.bhyy.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.model.*;
import com.hengsu.bhyy.core.service.*;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Appointment;
import com.hengsu.bhyy.core.repository.AppointmentRepository;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorConfigService doctorConfigService;

    @Autowired
    private HospitalService hospitalService;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DoctorVisitPlanService doctorVisitPlanService;


    @Transactional
    @Override
    public int create(AppointmentModel appointmentModel) {
        return appointmentRepo.insert(beanMapper.map(appointmentModel, Appointment.class));
    }

    @Transactional
    @Override
    public int createSelective(AppointmentModel appointmentModel) {
        if (StringUtils.isNotEmpty(appointmentModel.getVisitPlans())) {
            List<Long> ids = JSON.parseArray(appointmentModel.getVisitPlans(), Long.class);
            doctorVisitPlanService.updateStatus(ids, DoctorVisitPlanModel.USED);
        }

        DoctorModel doctorModel = doctorService.findByPrimaryKey(appointmentModel.getDoctorId());
        CustomerModel customerModel = customerService.findByPrimaryKey(appointmentModel.getCustomerId());
        CustomerModel patientModel = customerService.findByPrimaryKey(appointmentModel.getPatientId());
        DoctorConfigModel configModel = doctorConfigService.findByPrimaryKey(appointmentModel.getDoctorConfigId());
        HospitalModel hospitalModel = hospitalService.findByPrimaryKey(configModel.getHospitalId());


        Map<String, Object> snapshot = ImmutableMap.of("doctor", doctorModel, "customer", customerModel, "patient", patientModel);
        appointmentModel.setSnapshot(JSON.toJSONString(snapshot));


        //客户设置
        String customerSql = "update customer set appoint_num = appoint_num + 1 where id = ?";
        jdbcTemplate.update(customerSql, appointmentModel.getCustomerId());

        appointmentModel.setDoctorName(doctorModel.getRealName());
        appointmentModel.setCustomerName(customerModel.getRealName());
        appointmentModel.setPatientName(patientModel.getRealName());
        appointmentModel.setHospitalName(hospitalModel.getName());
        appointmentModel.setAddress(hospitalModel.getAddress());
        appointmentModel.setHospitalId(configModel.getHospitalId());
        appointmentModel.setCreateTime(new Date());
        appointmentModel.setAppointId(RandomUtil.getIdByUUIdAndTime());
        return appointmentRepo.insertSelective(beanMapper.map(appointmentModel, Appointment.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return appointmentRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public AppointmentModel findByPrimaryKey(Long id) {
        Appointment appointment = appointmentRepo.selectByPrimaryKey(id);
        return beanMapper.map(appointment, AppointmentModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(AppointmentModel appointmentModel) {
        return appointmentRepo.selectCount(beanMapper.map(appointmentModel, Appointment.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentModel> selectPage(AppointmentModel appointmentModel, Pageable pageable) {
        Appointment appointment = beanMapper.map(appointmentModel, Appointment.class);
        return beanMapper.mapAsList(appointmentRepo.selectPage(appointment, pageable), AppointmentModel.class);
    }

    @Override
    public Page<Map<String, Object>> searchAppointPage(Map<String, String> param, Pageable pageable) {

        String select = "select a.id,a.appoint_id as appointId,a.source, a.status,c.id as customerId,c.real_name as customerName,c.phone, ic.id as itemClassId,ic.CLASS_NAME as className,\n" +
                "d.id as doctorId,  d.real_name as doctorName,a.time";

        String tables = " FROM appointment a,customer c,doctor d,item_class ic ";

        StringBuffer condition = new StringBuffer();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        condition.append(" WHERE 1=1 and a.item_class_id=ic.id and d.id=a.doctor_id and c.id=a.patient_id");

        if (StringUtils.isNotEmpty(param.get("appointId"))) {
            condition.append(" and a.appoint_id like '%" + param.get("appointId") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("itemClassId"))) {
            condition.append(" and a.item_class_id=" + param.get("itemClassId") + "");
        }

        if (StringUtils.isNotEmpty(param.get("status"))) {
            condition.append(" and a.status=" + param.get("status"));
            if (StringUtils.isNotEmpty(param.get("startTime"))) {
                condition.append(" and a.time > '" + param.get("startTime") + "'");
            }

            if (StringUtils.isNotEmpty(param.get("endTime"))) {
                condition.append(" and a.time < '" + param.get("endTime") + "'");
            }

        } else {
            condition.append(" and ((a.status in (1)");
            if (StringUtils.isEmpty(param.get("startTime")) && StringUtils.isEmpty(param.get("endTime"))) {
//                condition.append(" and a.time < '" + simpleDateFormat.format(new Date()) + "'");
                condition.append(" or a.time >'" + simpleDateFormat.format(DateUtils.addDays(new Date(), 2)) + "'");
            }
            if (StringUtils.isNotEmpty(param.get("startTime"))) {
                condition.append(" and a.time > '" + param.get("startTime") + "'");
            }

            if (StringUtils.isNotEmpty(param.get("endTime"))) {
                condition.append(" and a.time < '" + param.get("endTime") + "'");
            }

            condition.append(") or a.status in (0,4) ) ");
        }


        if (StringUtils.isNotEmpty(param.get("source"))) {
            condition.append(" and a.source = " + param.get("source"));
        }


        if (StringUtils.isNotEmpty(param.get("phone"))) {
            condition.append(" and c.phone like '%" + param.get("phone") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("customerOrg"))) {
            condition.append(" and c.org like '%" + param.get("customerOrg") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("customerName"))) {
            condition.append(" and c.real_name like '%" + param.get("customerName") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("doctorName"))) {
            condition.append(" and d.real_name LIKE '%" + param.get("doctorName") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("hospitalName"))) {
            condition.append(" and a.hospital_name LIKE '%" + param.get("hospitalName") + "%'");
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
    public Page<Map<String, Object>> searchVisitPage(Map<String, String> param, Pageable pageable) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String select = "select a.id,a.appoint_id as appointId,a.source, a.status,c.id as customerId,c.real_name as customerName,c.phone, ic.id as itemClassId,ic.CLASS_NAME as className,\n" +
                "d.id as doctorId,  d.real_name as doctorName,a.time,a.type ";

        String tables = " FROM appointment a,customer c,doctor d,item_class ic ";

        StringBuffer condition = new StringBuffer();

        condition.append(" WHERE 1=1 and a.item_class_id=ic.id and d.id=a.doctor_id and c.id=a.patient_id");


        if (StringUtils.isNotEmpty(param.get("itemClassId"))) {
            condition.append(" and a.item_class_id=" + param.get("itemClassId") + "");
        }

        if (StringUtils.isNotEmpty(param.get("status"))) {
            condition.append(" and a.status=" + param.get("status"));
            if (StringUtils.isNotEmpty(param.get("startTime"))) {
                condition.append(" and a.time > '" + param.get("startTime") + "'");
            }

            if (StringUtils.isNotEmpty(param.get("endTime"))) {
                condition.append(" and a.time < '" + param.get("endTime") + "'");
            }

        } else {
            condition.append(" and ((a.status in (1)");
            if (StringUtils.isNotEmpty(param.get("startTime"))) {
                condition.append(" and a.time > '" + param.get("startTime") + "'");
            } else {
//                condition.append(" and a.time >'" + simpleDateFormat.format(new Date()) + "'");
            }

            if (StringUtils.isNotEmpty(param.get("endTime"))) {
                condition.append(" and a.time < '" + param.get("endTime") + "'");
            } else {
                condition.append(" and a.time <'" + simpleDateFormat.format(DateUtils.addDays(new Date(), 2)) + "'");
            }

            condition.append(") or a.status in (2,3) ) ");
        }

        if (StringUtils.isNotEmpty(param.get("type"))) {
            condition.append(" and a.type = " + param.get("type"));
        }

        if (StringUtils.isNotEmpty(param.get("startAge"))) {
            condition.append(" and c.age >= " + param.get("startAge"));
        }

        if (StringUtils.isNotEmpty(param.get("endAge"))) {
            condition.append(" and c.age <= " + param.get("endAge"));
        }


        if (StringUtils.isNotEmpty(param.get("phone"))) {
            condition.append(" and c.phone like '%" + param.get("phone") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("customerOrg"))) {
            condition.append(" and c.org like '%" + param.get("customerOrg") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("customerName"))) {
            condition.append(" and c.real_name like '%" + param.get("customerName") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("doctorName"))) {
            condition.append(" and d.real_name LIKE '%" + param.get("doctorName") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("hospitalName"))) {
            condition.append(" and a.hospital_name LIKE '%" + param.get("hospitalName") + "%'");
        }


        condition.append(" ORDER BY a.status,a.time DESC ");

        StringBuffer limitSql = new StringBuffer();
        if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
            limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
        }


        List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());


        //加评论
        List<Long> ids = new ArrayList<>();
        for (Map<String, Object> map : content) {
            Long id = Long.parseLong(map.get("id").toString());
            ids.add(id);
        }


        String commentSql = "SELECT a.id as appointmentId,bc.id,bc.bill_id as billId,bc.tags,bc.content,bc.create_time as createTime\n" +
                "FROM bill_comment bc, bill b, appointment a\n" +
                "WHERE a.id = b.appointment_id AND bc.bill_id = b.id AND a.id IN (" + StringUtils.join(ids, ",") + ")\n";


        List<Map<String, Object>> commentContent = jdbcTemplate.queryForList(commentSql);
        Map<Long, Map<String, Object>> commentMap = new HashMap<>();
        for (Map<String, Object> map : commentContent) {
            commentMap.put(Long.parseLong(map.get("appointmentId").toString()), map);
        }

        for (Map<String, Object> map : content) {
            Long id = Long.parseLong(map.get("id").toString());
            map.put("comment", commentMap.get(id));
        }


        Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);

        Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

        return page;
    }

    @Override
    public Page<Map<String, Object>> selectDoctorPage(Long doctorId, Map<String, String> param, Pageable pageable) {

        int status = Integer.parseInt(param.get("status").toString());

        if (status != 3) {
            String select = "SELECT\n" +
                    "  a.id,\n" +
                    "  a.customer_id AS customerId,\n" +
                    "  a.customer_name   AS customerName,\n" +
                    "  a.patient_name   AS patientName,\n" +
                    "  c.phone,\n" +
                    "  ic.CLASS_NAME    className,\n" +
                    "  a.time,\n" +
                    "  a.create_time AS createTime,\n" +
                    "  a.cancel_reason AS cancelReason,\n" +
                    "  a.address ,\n" +
                    "  a.remark ";
            String from = "FROM appointment a, customer c, item_class ic\n" +
                    "WHERE a.doctor_id = ?\n" +
                    "      AND a.customer_id = c.id AND a.item_class_id = ic.id and a.status=? ";

            if (StringUtils.isNotEmpty(param.get("keywords"))) {
                String keywords = param.get("keywords");
                from += " AND (a.patient_name LIKE '%" + keywords + "%' OR c.phone LIKE '%" + keywords + "%' OR ic.CLASS_NAME LIKE '%" + keywords + "%' )";
            }


            String orderBy = " ORDER BY a.time DESC";

            List<Map<String, Object>> content = jdbcTemplate.queryForList(select + from + orderBy + " limit ?,? ", doctorId, param, pageable.getOffset(), pageable.getPageSize());
            Long count = jdbcTemplate.queryForObject(" select count(*) " + from, Long.class, doctorId, status);
            Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);
            return page;

        } else if (status == 3) {


        }


        return null;
    }

    @Override
    public Page<Map<String, Object>> selectCustomerPage(Long customerId, int status, Pageable pageable) {
        if (status != 3) {
            String select = "SELECT\n" +
                    "  a.id,\n" +
                    "  a.customer_id AS customerId,\n" +
                    "  a.customer_name   AS customerName,\n" +
                    "  a.patient_name   AS patientName,\n" +
                    "  c.phone,\n" +
                    "  ic.CLASS_NAME    className,\n" +
                    "  a.time,\n" +
                    "  a.create_time AS createTime,\n" +
                    "  a.cancel_reason AS cancelReason,\n" +
                    "  a.address ,\n" +
                    "  a.remark ";
            String from = "FROM appointment a, customer c, item_class ic\n" +
                    "WHERE a.customer_id = ?\n" +
                    "      AND a.customer_id = c.id AND a.item_class_id = ic.id and a.status=? ";

            String orderBy = " ORDER BY a.time DESC";

            List<Map<String, Object>> content = jdbcTemplate.queryForList(select + from + orderBy + " limit ?,? ", customerId, status, pageable.getOffset(), pageable.getPageSize());
            Long count = jdbcTemplate.queryForObject(" select count(*) " + from, Long.class, customerId, status);
            Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);
            return page;

        } else if (status == 3) {
        }


        return null;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(AppointmentModel appointmentModel) {
        return appointmentRepo.updateByPrimaryKey(beanMapper.map(appointmentModel, Appointment.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(AppointmentModel appointmentModel) {
        //老计划解除
        AppointmentModel oldAppointmentModel = findByPrimaryKey(appointmentModel.getId());
        List<Long> oldIds = JSON.parseArray(oldAppointmentModel.getVisitPlans(), Long.class);
        doctorVisitPlanService.updateStatus(oldIds, DoctorVisitPlanModel.UNUSE);
        //新计划
        List<Long> ids = JSON.parseArray(appointmentModel.getVisitPlans(), Long.class);
        doctorVisitPlanService.updateStatus(ids, DoctorVisitPlanModel.USED);

        DoctorModel doctorModel = doctorService.findByPrimaryKey(appointmentModel.getDoctorId());
        CustomerModel customerModel = customerService.findByPrimaryKey(appointmentModel.getCustomerId());
        CustomerModel patientModel = customerService.findByPrimaryKey(appointmentModel.getPatientId());
        DoctorConfigModel configModel = doctorConfigService.findByPrimaryKey(appointmentModel.getDoctorConfigId());
        HospitalModel hospitalModel = hospitalService.findByPrimaryKey(configModel.getHospitalId());


        Map<String, Object> snapshot = ImmutableMap.of("doctor", doctorModel, "customer", customerModel, "patient", patientModel);
        appointmentModel.setSnapshot(JSON.toJSONString(snapshot));


        appointmentModel.setDoctorName(doctorModel.getRealName());
        appointmentModel.setCustomerName(customerModel.getRealName());
        appointmentModel.setPatientName(patientModel.getRealName());
        appointmentModel.setHospitalName(hospitalModel.getName());
        appointmentModel.setAddress(hospitalModel.getAddress());
        return appointmentRepo.updateByPrimaryKeySelective(beanMapper.map(appointmentModel, Appointment.class));
    }


}
