package com.hengsu.bhyy.core.service.impl;

import com.hengsu.bhyy.core.entity.Customer;
import com.hengsu.bhyy.core.model.CustomerModel;
import com.hengsu.bhyy.core.repository.CustomerRepository;
import com.hengsu.bhyy.core.service.CustomerService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CustomerRepository customerRepo;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int create(CustomerModel customerModel) {
        return customerRepo.insert(beanMapper.map(customerModel, Customer.class));
    }

    @Transactional
    @Override
    public int createSelective(CustomerModel customerModel) {
        Customer customer = beanMapper.map(customerModel, Customer.class);
        int retVal = customerRepo.insertSelective(customer);
        customerModel.setId(customer.getId());
        return retVal;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerModel findByPrimaryKey(Long id) {
        Customer customer = customerRepo.selectByPrimaryKey(id);
        return beanMapper.map(customer, CustomerModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(CustomerModel customerModel) {
        return customerRepo.selectCount(beanMapper.map(customerModel, Customer.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerModel> selectPage(CustomerModel customerModel, Pageable pageable) {
        Customer customer = beanMapper.map(customerModel, Customer.class);
        return beanMapper.mapAsList(customerRepo.selectPage(customer, pageable), CustomerModel.class);
    }

    @Override
    public List<Map<String, Object>> selectRelation(Long id) {

        String sql = "SELECT\n" +
                "  c.id,\n" +
                "  c.nick_name AS nickName,\n" +
                "  c.real_name AS realName,\n" +
                "  c.phone,\n" +
                "  c.gender,\n" +
                "  c.age,\n" +
                "  cr.relation,\n" +
                "  cr.id       AS relationId\n" +
                "FROM customer_relation cr\n" +
                "\n" +
                "  JOIN customer c ON cr.b_customer_id = c.id\n" +
                "                     AND cr.a_customer_id = ?";

        return jdbcTemplate.queryForList(sql, id);
    }

    @Override
    public List<Map<String, Object>> queryByName(String name) {

        //先查客户表,如果查出来的客户有手机号,则说明就诊人与预约人一直
        String sql = "SELECT id,real_name as realName,phone,type\n" +
                "FROM customer WHERE real_name LIKE  '%" + name + "%';";
        String relationSql = "SELECT c.id,c.real_name as realName,c.phone\n" +
                "FROM customer c, customer_relation cr\n" +
                "WHERE\n" +
                "  c.id=cr.a_customer_id and\n" +
                "  cr.b_customer_id=?";
        List<CustomerModel> customerModels = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomerModel.class));
        List<Map<String, Object>> results = new ArrayList<>();
        for (CustomerModel customerModel : customerModels) {

            if (CustomerModel.TYPE_REGISTER == customerModel.getType()) {
                Map<String, Object> map = new HashMap<>();
                map.put("customerId", customerModel.getId());
                map.put("customerName", customerModel.getRealName());
                map.put("phone", customerModel.getPhone());
                map.put("isPatient", true);
                results.add(map);
            } else {
                List<CustomerModel> relationCustomerModels = jdbcTemplate.query(relationSql,
                        new BeanPropertyRowMapper<>(CustomerModel.class), customerModel.getId());
                for (CustomerModel relationCustomerModel : relationCustomerModels) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", customerModel.getId());
                    map.put("relationId", relationCustomerModel.getId());
                    map.put("relationName", relationCustomerModel.getRealName());
                    map.put("relationPhone", relationCustomerModel.getPhone());
                    map.put("isPatient", false);
                    results.add(map);
                }

            }
        }
        return results;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(CustomerModel customerModel) {
        return customerRepo.updateByPrimaryKey(beanMapper.map(customerModel, Customer.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(CustomerModel customerModel) {
        return customerRepo.updateByPrimaryKeySelective(beanMapper.map(customerModel, Customer.class));
    }

}
