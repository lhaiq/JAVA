package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CaseImage;
import com.hengsu.bhyy.core.repository.CaseImageRepository;
import com.hengsu.bhyy.core.model.CaseImageModel;
import com.hengsu.bhyy.core.service.CaseImageService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;
import java.util.Map;

@Service
public class CaseImageServiceImpl implements CaseImageService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CaseImageRepository caseImageRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int create(CaseImageModel caseImageModel) {
        return caseImageRepo.insert(beanMapper.map(caseImageModel, CaseImage.class));
    }

    @Transactional
    @Override
    public int createSelective(CaseImageModel caseImageModel) {
        return caseImageRepo.insertSelective(beanMapper.map(caseImageModel, CaseImage.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return caseImageRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public CaseImageModel findByPrimaryKey(Long id) {
        CaseImage caseImage = caseImageRepo.selectByPrimaryKey(id);
        return beanMapper.map(caseImage, CaseImageModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(CaseImageModel caseImageModel) {
        return caseImageRepo.selectCount(beanMapper.map(caseImageModel, CaseImage.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CaseImageModel> selectPage(CaseImageModel caseImageModel, Pageable pageable) {
        CaseImage caseImage = beanMapper.map(caseImageModel, CaseImage.class);
        return beanMapper.mapAsList(caseImageRepo.selectPage(caseImage, pageable), CaseImageModel.class);
    }

    @Override
    public Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable) {
        String select = "SELECT\n" +
                "  ci.id,\n" +
                "  ci.film_time AS filmTime,\n" +
                "  c.real_name  AS customerName,\n" +
                "  c.phone,\n" +
                "  c.gender ";

        String tables = " FROM case_image ci, appointment a, customer c\n" +
                "WHERE ci.appoint_id = a.id AND a.customer_id = c.id ";

        StringBuffer condition = new StringBuffer();


        if (StringUtils.isNotEmpty(param.get("gender"))) {
            condition.append(" and c.gender = " + param.get("gender"));
        }


        if (StringUtils.isNotEmpty(param.get("startDate"))) {
            condition.append(" and ci.film_time >= '" + param.get("startDate") + "'");
        }

        if (StringUtils.isNotEmpty(param.get("endDate"))) {
            condition.append(" and ci.film_time <= '" + param.get("endDate") + "'");
        }


        if (StringUtils.isNotEmpty(param.get("phone"))) {
            condition.append(" and c.phone like '%" + param.get("phone") + "%'");
        }

        if (StringUtils.isNotEmpty(param.get("customerName"))) {
            condition.append(" and c.real_name like '%" + param.get("customerName") + "%'");
        }


        StringBuffer limitSql = new StringBuffer();
        limitSql.append(" order by ci.film_time desc");
        if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
            limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
        }


        List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + condition.toString() + limitSql.toString());
        Long count = jdbcTemplate.queryForObject("select count(*) " + tables + condition.toString(), Long.class);

        Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

        return page;
    }

    @Override
    public Page<Map<String, Object>> customerPage(Long customerId, Pageable pageable) {
        return null;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(CaseImageModel caseImageModel) {
        return caseImageRepo.updateByPrimaryKey(beanMapper.map(caseImageModel, CaseImage.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(CaseImageModel caseImageModel) {
        return caseImageRepo.updateByPrimaryKeySelective(beanMapper.map(caseImageModel, CaseImage.class));
    }


}
