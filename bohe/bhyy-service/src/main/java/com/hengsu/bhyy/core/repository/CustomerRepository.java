package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Customer;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("customer") Customer customer);

    int insertSelective(@Param("customer") Customer customer);

    Customer selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("customer") Customer customer);

    int updateByPrimaryKey(@Param("customer") Customer customer);

    int selectCount(@Param("customer") Customer customer);

    List<Customer> selectPage(@Param("customer") Customer customer, @Param("pageable") Pageable pageable);

    List<Customer> selectRelation(@Param("id") Long id);
}