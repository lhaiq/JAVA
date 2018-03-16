
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CustomerModel;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    int create(CustomerModel customerModel);

    int createSelective(CustomerModel customerModel);

    CustomerModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerModel customerModel);

    int updateByPrimaryKeySelective(CustomerModel customerModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(CustomerModel customerModel);

    List<CustomerModel> selectPage(CustomerModel customerModel, Pageable pageable);

    List<Map<String,Object>>  selectRelation(Long id);


    List<Map<String,Object>> queryByName(String name);

}