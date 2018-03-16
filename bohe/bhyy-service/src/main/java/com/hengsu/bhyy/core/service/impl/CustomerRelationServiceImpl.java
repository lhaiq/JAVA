package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.CustomerRelation;
import com.hengsu.bhyy.core.repository.CustomerRelationRepository;
import com.hengsu.bhyy.core.model.CustomerRelationModel;
import com.hengsu.bhyy.core.service.CustomerRelationService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class CustomerRelationServiceImpl implements CustomerRelationService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CustomerRelationRepository customerRelationRepo;

    @Autowired
    private CustomerService customerService;

    @Transactional
    @Override
    public int create(CustomerRelationModel customerRelationModel) {
        return customerRelationRepo.insert(beanMapper.map(customerRelationModel, CustomerRelation.class));
    }

    @Transactional
    @Override
    public int createSelective(CustomerRelationModel customerRelationModel) {
        return customerRelationRepo.insertSelective(beanMapper.map(customerRelationModel, CustomerRelation.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerRelationRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerRelationModel findByPrimaryKey(Long id) {
        CustomerRelation customerRelation = customerRelationRepo.selectByPrimaryKey(id);
        return beanMapper.map(customerRelation, CustomerRelationModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(CustomerRelationModel customerRelationModel) {
        return customerRelationRepo.selectCount(beanMapper.map(customerRelationModel, CustomerRelation.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerRelationModel> selectPage(CustomerRelationModel customerRelationModel, Pageable pageable) {
        CustomerRelation customerRelation = beanMapper.map(customerRelationModel, CustomerRelation.class);
        return beanMapper.mapAsList(customerRelationRepo.selectPage(customerRelation, pageable), CustomerRelationModel.class);
    }

    @Transactional
    @Override
    public void addRelation(Long customerId, CustomerModel customerModel,int relation) {

        customerService.createSelective(customerModel);

        CustomerRelationModel customerRelationModel = new CustomerRelationModel();
        customerRelationModel.setaCustomerId(customerId);
        customerRelationModel.setbCustomerId(customerModel.getId());
        customerRelationModel.setRelation(relation);
        createSelective(customerRelationModel);
    }

    @Transactional
    @Override
    public void updateRelation(Long customerId, Long relationId, CustomerModel customerModel, int relation) {
        customerModel.setId(customerId);
        customerService.updateByPrimaryKeySelective(customerModel);

        CustomerRelationModel customerRelationModel = new CustomerRelationModel();
        customerRelationModel.setId(relationId);
        customerRelationModel.setRelation(relation);
        updateByPrimaryKeySelective(customerRelationModel);

    }

    @Transactional
    @Override
    public int updateByPrimaryKey(CustomerRelationModel customerRelationModel) {
        return customerRelationRepo.updateByPrimaryKey(beanMapper.map(customerRelationModel, CustomerRelation.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(CustomerRelationModel customerRelationModel) {
        return customerRelationRepo.updateByPrimaryKeySelective(beanMapper.map(customerRelationModel, CustomerRelation.class));
    }

}
