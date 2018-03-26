package com.hengsu.bhyy.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.hengsu.bhyy.core.model.WalletModel;
import com.hengsu.bhyy.core.service.BillService;
import com.hengsu.bhyy.core.service.CustomerService;
import com.hengsu.bhyy.core.service.WalletService;
import org.apache.commons.collections4.CollectionUtils;
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
    public List<DoctorModel> selectPage(DoctorModel doctorModel, Pageable pageable) {
        Doctor doctor = beanMapper.map(doctorModel, Doctor.class);
        return beanMapper.mapAsList(doctorRepo.selectPage(doctor, pageable), DoctorModel.class);
    }

    @Override
    public Page<Map<String, Object>> searchPage(Map<String, Object> param, Pageable pageable) {
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

        if (param.containsKey("id")) {
            condition.append(" and d.id like '%" + param.get("id") + "%' ");
        }

        if (param.containsKey("phone")) {
            condition.append(" and d.phone like '%" + param.get("phone") + "%'");
        }

        if (param.containsKey("inviteCode")) {
            condition.append(" and d.invite_code=" + param.get("inviteCode"));
        }

        if (param.containsKey("source")) {
            condition.append(" and d.source=" + param.get("source"));
        }

        if (param.containsKey("status")) {
            condition.append(" and d.status=" + param.get("status"));
        }

        if (param.containsKey("startDate")) {
            condition.append(" and d.add_date>='" + param.get("startDate") + "'");
        }

        if (param.containsKey("endDate")) {
            condition.append(" and d.add_date<='" + param.get("endDate") + "'");
        }

        if (param.containsKey("name")) {
            condition.append(" and d.real_name like '%" + param.get("name") + "%'");
        }

        if (param.containsKey("hospitalName")) {
            condition.append(" and d.hospital_name like '%" + param.get("hospitalName") + "%'");
        }

        if (param.containsKey("adept")) {
            List<String> adeptArray = (List) param.get("adept");
            condition.append(" and (");
            for (int i = 0; i < adeptArray.size(); i++) {
                if (i != 0) {
                    condition.append(" or ");
                }
                condition.append(" d.adept like '%" + adeptArray.get(i) + "%'");
            }

            condition.append(" )");

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
    public Page<Map<String, Object>> searchPageForApp(List<Long> dayOfWeek, String name, String itemName, Pageable pageable) {

        String select = "SELECT d.id,d.real_name as realName,d.hospital_name as hospitalName,d.icon,d.education,d.is_recommend as isRecommend ";

        String tables = " FROM doctor d  ";

        StringBuffer condition = new StringBuffer();


        condition.append(" WHERE 1=1 ");


        if (CollectionUtils.isNotEmpty(dayOfWeek)) {
            condition.append(" and d.id in (\n" +
                    "  SELECT doctor_id\n" +
                    "  FROM doctor_config where 1=1 \n");
            condition.append(" and day_of_week in (" + StringUtils.join(dayOfWeek, ",") + ") ");
            condition.append(")");
        }



        if (StringUtils.isNotEmpty(name)) {
            condition.append(" and d.real_name like '%" + name + "%'");
        }

        if (StringUtils.isNotEmpty(itemName)) {
            condition.append(" and d.service_item like '%" + itemName + "%'");
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
    public void addBalance(Long id, Long billId, BigDecimal money) {
        WalletModel walletModel = new WalletModel();
        walletModel.setCreateTime(new Date());
        walletModel.setDoctorId(id);
        walletModel.setBillId(billId);
        walletModel.setName(billService.findByPrimaryKey(billId).getItemName());
        walletModel.setMoney(money);
        walletService.createSelective(walletModel);

        //更新医生
        String doctorSql = "update doctor set balance = balance + ? where id = ?";
        jdbcTemplate.update(doctorSql, money, id);

        //更新客户
        String customerSql = "update customer set pay_money = pay_money + ? where id = ?";
        jdbcTemplate.update(customerSql, money, id);


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
