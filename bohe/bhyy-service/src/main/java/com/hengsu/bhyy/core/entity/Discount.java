package com.hengsu.bhyy.core.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Discount {
    private Long id;

    private BigDecimal limitPercentage;

    private Date createTime;

    private Integer status;

    private Integer rank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLimitPercentage() {
        return limitPercentage;
    }

    public void setLimitPercentage(BigDecimal limitPercentage) {
        this.limitPercentage = limitPercentage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}