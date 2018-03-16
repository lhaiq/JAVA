package com.hengsu.bhyy.core.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Customer {
    private Long id;

    private String nickName;

    private String realName;

    private Integer gender;

    private String phone;

    private Date birthday;

    private Integer age;

    private Integer source;

    private Date createTime;

    private String icon;

    private Integer appointNum;

    private Integer treatNum;

    private BigDecimal payMoney;

    private String org;

    private Integer type;

    private Integer relation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getAppointNum() {
        return appointNum;
    }

    public void setAppointNum(Integer appointNum) {
        this.appointNum = appointNum;
    }

    public Integer getTreatNum() {
        return treatNum;
    }

    public void setTreatNum(Integer treatNum) {
        this.treatNum = treatNum;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Integer getRelation() {
        return relation;
    }
}