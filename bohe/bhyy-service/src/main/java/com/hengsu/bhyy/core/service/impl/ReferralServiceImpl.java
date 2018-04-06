package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.model.*;
import com.hengsu.bhyy.core.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Referral;
import com.hengsu.bhyy.core.repository.ReferralRepository;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReferralServiceImpl implements ReferralService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private ReferralRepository referralRepo;

    @Autowired
    private ReferralLogService referralLogService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ReferralRelationService referralRelationService;

    @Autowired
    private BillService billService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int create(ReferralModel referralModel) {
        return referralRepo.insert(beanMapper.map(referralModel, Referral.class));
    }

    @Transactional
    @Override
    public int createSelective(ReferralModel referralModel) {
        Referral referral = beanMapper.map(referralModel, Referral.class);
        int retVal = referralRepo.insertSelective(referral);
        referralModel.setId(referral.getId());
        return retVal;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return referralRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ReferralModel findByPrimaryKey(Long id) {
        Referral referral = referralRepo.selectByPrimaryKey(id);
        return beanMapper.map(referral, ReferralModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(ReferralModel referralModel) {
        return referralRepo.selectCount(beanMapper.map(referralModel, Referral.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReferralModel> selectPage(ReferralModel referralModel, Pageable pageable) {
        Referral referral = beanMapper.map(referralModel, Referral.class);
        return beanMapper.mapAsList(referralRepo.selectPage(referral, pageable), ReferralModel.class);
    }

    @Transactional
    @Override
    public void scan(ReferralModel referralModel) {

        if (null != referralModel.getCustomerId()) {
            String sql = "SELECT id from referral WHERE customer_id = ? and appoint_id IS null";
            List<Long> ids = jdbcTemplate.queryForList(sql, Long.class, referralModel.getCustomerId());
            if (CollectionUtils.isNotEmpty(ids)) {
                sql = "UPDATE referral SET status = -1 WHERE ID IN (" + StringUtils.join(ids, ",") + ");";
                jdbcTemplate.update(sql);
            }

            sql= "UPDATE referral_relation SET status = 0 where doctor_id = ? and customer_id = ?";
            jdbcTemplate.update(sql,referralModel.getDoctorId(),referralModel.getCustomerId());
        }

        referralModel.setOperationTime(new Date());
        createSelective(referralModel);
        DoctorModel doctorModel = doctorService.findByPrimaryKey(referralModel.getDoctorId());
        ReferralLogModel referralLogModel = new ReferralLogModel();
        referralLogModel.setReferralId(referralModel.getId());
        referralLogModel.setCreateTime(new Date());
        referralLogModel.setContent("已完成扫描" + doctorModel.getRealName() + "医生二维码");
        referralLogService.createSelective(referralLogModel);

        //绑定关系
        if (null != referralModel.getCustomerId()) {
           ReferralRelationModel  referralRelationModel = new ReferralRelationModel();
            referralRelationModel.setCustomerId(referralModel.getCustomerId());
            referralRelationModel.setDoctorId(referralModel.getDoctorId());
            referralRelationModel.setStatus(1);
            referralRelationService.createSelective(referralRelationModel);
        }

    }

    @Transactional
    @Override
    public void consult(Long id) {
        ReferralModel referralModel = new ReferralModel();
        referralModel.setId(id);
        referralModel.setOperationTime(new Date());
        referralModel.setStatus(1);
        updateByPrimaryKeySelective(referralModel);
        ReferralLogModel referralLogModel = new ReferralLogModel();
        referralLogModel.setReferralId(referralModel.getId());
        referralLogModel.setCreateTime(new Date());
        referralLogModel.setContent("咨询中");
        referralLogService.createSelective(referralLogModel);

    }

    @Transactional
    @Override
    public void sendReport(List<Long> ids) {
        for (Long id : ids) {
            ReferralModel referralModel = new ReferralModel();
            referralModel.setId(id);
            referralModel.setIsSend(1);
            updateByPrimaryKeySelective(referralModel);
        }

    }

    @Override
    public Page<Map<String, Object>> searchAppointPage(Map<String, String> param, Pageable pageable) {
        String select = "SELECT r.id,c.real_name as customerName,c.phone,d.real_name as doctorName," +
                "r.status,r.type,r.num,r.operation_time as operationTime,r.is_send AS isSend ";

        String tables = " from referral r\n" +
                "  LEFT JOIN customer c on r.customer_id = c.id\n" +
                "  JOIN doctor d on r.doctor_id=d.id  ";

        StringBuffer condition = new StringBuffer();

        condition.append(" WHERE 1=1 ");

        if (StringUtils.isNotEmpty(param.get("status"))) {
            condition.append(" and r.status = " + param.get("status"));
        }

        if (StringUtils.isNotEmpty(param.get("type"))) {
            condition.append(" and r.type = " + param.get("type"));
        }

        if (StringUtils.isNotEmpty(param.get("startNum"))) {
            condition.append(" and r.num >= " + param.get("startNum"));
        }

        if (StringUtils.isNotEmpty(param.get("endNum"))) {
            condition.append(" and r.num <= " + param.get("endNum"));
        }

        if (StringUtils.isNotEmpty(param.get("startDate"))) {
            condition.append(" and r.operation_time >= '" + param.get("startDate") + "'");
        }

        if (StringUtils.isNotEmpty(param.get("endDate"))) {
            condition.append(" and r.operation_time <= '" + param.get("endDate") + "'");
        }


        if (StringUtils.isNotEmpty(param.get("phone"))) {
            condition.append(" and c.phone like '%" + param.get("phone") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("customerName"))) {
            condition.append(" and c.real_name like '%" + param.get("customerName") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("doctorName"))) {
            condition.append(" and d.real_name LIKE '%" + param.get("doctorName") + "%'");
        }

        if (null != pageable.getSort()) {
            condition.append(" order by ");
            List<String> sortStr = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                sortStr.add(order.getProperty() + " " + order.getDirection());
            }
            condition.append(StringUtils.join(sortStr, ","));
        }else {
            condition.append(" order by r.operation_time desc");
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
    public Page<Map<String, Object>> selectReferralingPage(Long doctorId,List<Integer> statuses,String startDate,String endDate, Pageable pageable) {
        String select = "SELECT r.id,r.operation_time as operationTime,r.status,r.customer_id as customerId,r.doctor_id as doctorId," +
                "c.real_name as customerName, c.icon ";

        String tables = " FROM referral r,customer c where r.customer_id=c.id  AND r.doctor_id = "+doctorId;

        StringBuffer condition = new StringBuffer();
        condition.append(" and r.status in ("+StringUtils.join(statuses,",")+")");
        condition.append(" and r.operation_time <= '"+endDate+"'");
        condition.append(" and r.operation_time >= '"+startDate+"'");


        StringBuffer limitSql = new StringBuffer();
        limitSql.append(" order by r.operation_time desc");
        if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
            limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
        }


        List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
        Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);

        Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

        return page;
    }

    @Override
    public Map<String, Object> selectReferraledPage(Long doctorId,List<Integer> statuses, String startDate,String endDate,Pageable pageable) {
        String select = "SELECT r.id,r.operation_time as operationTime,r.bill_id AS billId,r.status,r.customer_id as customerId,r.doctor_id as doctorId,c.real_name as customerName, c.icon ";

        String tables = " FROM referral r,customer c where r.customer_id=c.id AND r.doctor_id = "+doctorId;

        StringBuffer condition = new StringBuffer();
        condition.append(" and r.status in ("+StringUtils.join(statuses,",")+")");
        condition.append(" and r.operation_time <= '"+endDate+"'");
        condition.append(" and r.operation_time >= '"+startDate+"'");


        StringBuffer limitSql = new StringBuffer();
        limitSql.append(" order by r.operation_time desc");
        if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
            limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
        }


        List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());

        if(CollectionUtils.isNotEmpty(content)){
            List<Long> billIds = content.stream().map(e->Long.parseLong(e.get("billId").toString())).collect(Collectors.toList());
            String billSql = "SELECT id,actual_cost as actualCost\n" +
                    "FROM bill where id in  ("+StringUtils.join(billIds,",")+")";
            List<BillModel> billModels = jdbcTemplate.query(billSql,new BeanPropertyRowMapper<>(BillModel.class));
            Map<Long,BigDecimal> map = billModels.stream().collect(Collectors.toMap(BillModel::getId,BillModel::getActualCost));
            content.stream().forEach(e->{
                Long billId = Long.parseLong(e.get("billId").toString());
                e.put("money",map.get(billId));
            });
        }

        //总金额
        StringBuffer totalSql = new StringBuffer();
        totalSql.append("SELECT sum(b.actual_cost) from referral r ,bill b WHERE r.bill_id=b.id AND r.doctor_id = "+doctorId);
        totalSql.append(" and r.status in ("+StringUtils.join(statuses,",")+")");
        totalSql.append(" and r.operation_time <= '"+endDate+"'");
        totalSql.append(" and r.operation_time >= '"+startDate+"'");
        BigDecimal totalMoney = jdbcTemplate.queryForObject(totalSql.toString(),BigDecimal.class);
        Map<String,Object> data = new HashMap<>();




        Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);

        Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);
        data.put("page",page);
        data.put("totalMoney",totalMoney);
        return data;
    }

    @Transactional
    @Override
    public void bindCustomerAfterRegister(Long id, Long customerId,String message) {

        //绑定转诊信息
        ReferralModel referralParam = new ReferralModel();
        referralParam.setId(id);
        referralParam.setCustomerId(customerId);
        referralParam.setStatus(2);
        updateByPrimaryKeySelective(referralParam);

        //添加关系
        ReferralModel referralModel = findByPrimaryKey(id);
        ReferralRelationModel referralRelationModel = new ReferralRelationModel();
        referralRelationModel.setCustomerId(customerId);
        referralRelationModel.setDoctorId(referralModel.getDoctorId());
        referralRelationService.createSelective(referralRelationModel);

        //添加日志
        ReferralLogModel referralLogModel = new ReferralLogModel();
        referralLogModel.setReferralId(referralModel.getId());
        referralLogModel.setCreateTime(new Date());
        referralLogModel.setContent(message);
        referralLogService.createSelective(referralLogModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(ReferralModel referralModel) {
        return referralRepo.updateByPrimaryKey(beanMapper.map(referralModel, Referral.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(ReferralModel referralModel) {
        return referralRepo.updateByPrimaryKeySelective(beanMapper.map(referralModel, Referral.class));
    }

}
