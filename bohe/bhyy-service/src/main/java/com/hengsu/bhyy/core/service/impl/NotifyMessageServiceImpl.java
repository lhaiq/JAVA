package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.NotifyMessage;
import com.hengsu.bhyy.core.repository.NotifyMessageRepository;
import com.hengsu.bhyy.core.model.NotifyMessageModel;
import com.hengsu.bhyy.core.service.NotifyMessageService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;
import java.util.Map;

@Service
public class NotifyMessageServiceImpl implements NotifyMessageService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private NotifyMessageRepository notifyMessageRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int create(NotifyMessageModel notifyMessageModel) {
        return notifyMessageRepo.insert(beanMapper.map(notifyMessageModel, NotifyMessage.class));
    }

    @Transactional
    @Override
    public int createSelective(NotifyMessageModel notifyMessageModel) {
        return notifyMessageRepo.insertSelective(beanMapper.map(notifyMessageModel, NotifyMessage.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return notifyMessageRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public NotifyMessageModel findByPrimaryKey(Long id) {
        NotifyMessage notifyMessage = notifyMessageRepo.selectByPrimaryKey(id);
        return beanMapper.map(notifyMessage, NotifyMessageModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(NotifyMessageModel notifyMessageModel) {
        return notifyMessageRepo.selectCount(beanMapper.map(notifyMessageModel, NotifyMessage.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotifyMessageModel> selectPage(NotifyMessageModel notifyMessageModel, Pageable pageable) {
        NotifyMessage notifyMessage = beanMapper.map(notifyMessageModel, NotifyMessage.class);
        return beanMapper.mapAsList(notifyMessageRepo.selectPage(notifyMessage, pageable), NotifyMessageModel.class);
    }

    @Override
    public Page<Map<String, Object>> selectByDoctorId(Long doctorId, Pageable pageable) {
        String select = " SELECT nm.id,nm.doctor_id as doctorId,nm.message,nm.create_time as createTime,d.real_name as realName,d.phone ";
        String tables = " FROM notify_message nm,doctor d where nm.doctor_id=d.id and nm.doctor_id="+doctorId;


        StringBuffer limitSql = new StringBuffer();
        if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
            limitSql.append(" limit " + pageable.getOffset() + "," + pageable.getPageSize());
        }

        List<Map<String, Object>> content = jdbcTemplate.queryForList(select + tables + limitSql.toString());
        Long count = jdbcTemplate.queryForObject("select count(*) " + tables, Long.class);
        Page<Map<String, Object>> page = new PageImpl<>(content, pageable, count);

        return page;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(NotifyMessageModel notifyMessageModel) {
        return notifyMessageRepo.updateByPrimaryKey(beanMapper.map(notifyMessageModel, NotifyMessage.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(NotifyMessageModel notifyMessageModel) {
        return notifyMessageRepo.updateByPrimaryKeySelective(beanMapper.map(notifyMessageModel, NotifyMessage.class));
    }

}
