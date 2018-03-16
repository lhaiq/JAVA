
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.model.CustomerRelationModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerRelationService {

    int create(CustomerRelationModel customerRelationModel);

    int createSelective(CustomerRelationModel customerRelationModel);

    CustomerRelationModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerRelationModel customerRelationModel);

    int updateByPrimaryKeySelective(CustomerRelationModel customerRelationModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(CustomerRelationModel customerRelationModel);

    List<CustomerRelationModel> selectPage(CustomerRelationModel customerRelationModel, Pageable pageable);

    void addRelation(Long customerId, CustomerModel customerModel,int relation);

    void updateRelation(Long customerId, Long relationId, CustomerModel customerModel,int relation);

}