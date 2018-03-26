package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.model.DoctorModel;
import com.hengsu.bhyy.core.model.ReferralLogModel;
import com.hengsu.bhyy.core.service.DoctorService;
import com.hengsu.bhyy.core.service.ReferralLogService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Referral;
import com.hengsu.bhyy.core.repository.ReferralRepository;
import com.hengsu.bhyy.core.model.ReferralModel;
import com.hengsu.bhyy.core.service.ReferralService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
//		if(null==referralModel.getCustomerId()){
        referralModel.setOperationTime(new Date());
        createSelective(referralModel);
        DoctorModel doctorModel = doctorService.findByPrimaryKey(referralModel.getDoctorId());
        ReferralLogModel referralLogModel = new ReferralLogModel();
        referralLogModel.setReferralId(referralModel.getId());
        referralLogModel.setCreateTime(new Date());
        referralLogModel.setContent("已完成扫描" + doctorModel.getRealName() + "医生二维码");
        referralLogService.createSelective(referralLogModel);
        //TODO

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
        for(Long id:ids){
            ReferralModel referralModel = new ReferralModel();
            referralModel.setId(id);
            referralModel.setIsSend(1);
            updateByPrimaryKeySelective(referralModel);
        }

    }

    @Override
    public Page<Map<String, Object>> searchAppointPage(Map<String, String> param, Pageable pageable) {
        String select = "SELECT r.id,c.real_name as customerName,c.phone,d.real_name as doctorName," +
                "r.status,r.type,r.num,r.operation_time as operationTime ";

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
            condition.append(" and r.num >= '" + param.get("startDate") + "'");
        }

        if (StringUtils.isNotEmpty(param.get("endDate"))) {
            condition.append(" and r.num <= '" + param.get("endDate") + "'");
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
