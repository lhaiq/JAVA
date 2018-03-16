package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.entity.DoctorVisitPlan;
import com.hengsu.bhyy.core.model.DoctorConfigModel;
import com.hengsu.bhyy.core.model.DoctorVisitPlanModel;
import com.hengsu.bhyy.core.repository.DoctorVisitPlanRepository;
import com.hengsu.bhyy.core.service.DoctorVisitPlanService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DoctorVisitPlanServiceImpl implements DoctorVisitPlanService {

    private final Logger logger = LoggerFactory.getLogger(DoctorVisitPlanServiceImpl.class);


    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private DoctorVisitPlanRepository doctorVisitPlanRepo;

    @Autowired
    private DoctorVisitPlanService doctorVisitPlanService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int create(DoctorVisitPlanModel doctorVisitPlanModel) {
        return doctorVisitPlanRepo.insert(beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class));
    }

    @Transactional
    @Override
    public int createSelective(DoctorVisitPlanModel doctorVisitPlanModel) {
        return doctorVisitPlanRepo.insertSelective(beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return doctorVisitPlanRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public DoctorVisitPlanModel findByPrimaryKey(Long id) {
        DoctorVisitPlan doctorVisitPlan = doctorVisitPlanRepo.selectByPrimaryKey(id);
        return beanMapper.map(doctorVisitPlan, DoctorVisitPlanModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(DoctorVisitPlanModel doctorVisitPlanModel) {
        return doctorVisitPlanRepo.selectCount(beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorVisitPlanModel> selectPage(DoctorVisitPlanModel doctorVisitPlanModel, Pageable pageable) {
        DoctorVisitPlan doctorVisitPlan = beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class);
        return beanMapper.mapAsList(doctorVisitPlanRepo.selectPage(doctorVisitPlan, pageable), DoctorVisitPlanModel.class);
    }

    @Transactional
    @Override
    public void addPlan(DoctorConfigModel configModel) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            long startTime = simpleDateFormat.parse(configModel.getStartTime()).getTime();
            long endTime = simpleDateFormat.parse(configModel.getEndTime()).getTime();
            for (long time = startTime; time <= endTime; time += configModel.getInterval() * 60 * 1000) {
                String timeStr = simpleDateFormat.format(new Date(time));
                DoctorVisitPlanModel doctorVisitPlanModel = new DoctorVisitPlanModel();
                doctorVisitPlanModel.setConfigId(configModel.getId());
                doctorVisitPlanModel.setDate(configModel.getDate());
                doctorVisitPlanModel.setTime(timeStr);
                doctorVisitPlanModel.setConfigId(configModel.getId());
                doctorVisitPlanModel.setTimeRange(time <= 14400000L ? DoctorVisitPlanModel.AM : DoctorVisitPlanModel.PM);
                createSelective(doctorVisitPlanModel);
            }

        } catch (ParseException e) {
            logger.error("unexpected error", e);
        }

    }

    @Transactional
    @Override
    public void updatePlan(DoctorConfigModel configModel) {

        //先删除未定的
        DoctorVisitPlanModel param = new DoctorVisitPlanModel();
        param.setConfigId(configModel.getId());
        param.setStatus(DoctorVisitPlanModel.UNUSE);
        doctorVisitPlanService.deleteSelective(param);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try{
            List<Long> usedTimes = new ArrayList<>();
            param.setStatus(DoctorVisitPlanModel.USED);
            List<DoctorVisitPlanModel> doctorVisitPlanModels = selectPage(param,new PageRequest(0,Integer.MAX_VALUE));
            for(DoctorVisitPlanModel doctorVisitPlanModel:doctorVisitPlanModels){
                long time = simpleDateFormat.parse(doctorVisitPlanModel.getTime()).getTime();
                usedTimes.add(time);
            }


            long startTime = simpleDateFormat.parse(configModel.getStartTime()).getTime();
            long endTime = simpleDateFormat.parse(configModel.getEndTime()).getTime();
            for (long time = startTime; time <= endTime; time += configModel.getInterval() * 60 * 1000) {
                if(isSkip(usedTimes,time,configModel.getInterval())){
                    continue;
                }

                String timeStr = simpleDateFormat.format(new Date(time));
                DoctorVisitPlanModel doctorVisitPlanModel = new DoctorVisitPlanModel();
                doctorVisitPlanModel.setConfigId(configModel.getId());
                doctorVisitPlanModel.setDate(configModel.getDate());
                doctorVisitPlanModel.setTime(timeStr);
                doctorVisitPlanModel.setTimeRange(time <= 14400000L ? DoctorVisitPlanModel.AM : DoctorVisitPlanModel.PM);
                createSelective(doctorVisitPlanModel);
            }


        }catch (ParseException e){
            logger.error("unexpected error", e);
        }

    }



    private boolean isSkip(List<Long> usedTimes,Long time,int interval){
        for(long usedTime:usedTimes){
            if(usedTime>=time&&usedTime<time+interval * 60 * 1000){
                return true;
            }
        }

        return false;
    }

    @Transactional
    @Override
    public void deleteSelective(DoctorVisitPlanModel doctorVisitPlanModel) {
        doctorVisitPlanRepo.deleteSelective(beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class));
    }

    @Transactional
    @Override
    public void updateStatus(List<Long> ids, int status) {
        String idsStr = StringUtils.join(ids,",");
        String sql = "update doctor_visit_plan set status = ? where id in ("+idsStr+") ";
        jdbcTemplate.update(sql,status);
    }


    @Transactional
    @Override
    public int updateByPrimaryKey(DoctorVisitPlanModel doctorVisitPlanModel) {
        return doctorVisitPlanRepo.updateByPrimaryKey(beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(DoctorVisitPlanModel doctorVisitPlanModel) {
        return doctorVisitPlanRepo.updateByPrimaryKeySelective(beanMapper.map(doctorVisitPlanModel, DoctorVisitPlan.class));
    }


}
