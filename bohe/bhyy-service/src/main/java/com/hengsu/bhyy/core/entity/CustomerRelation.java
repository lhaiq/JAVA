package com.hengsu.bhyy.core.entity;

public class CustomerRelation {
    private Long id;

    private Long aCustomerId;

    private Long bCustomerId;

    private Integer relation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getaCustomerId() {
        return aCustomerId;
    }

    public void setaCustomerId(Long aCustomerId) {
        this.aCustomerId = aCustomerId;
    }

    public Long getbCustomerId() {
        return bCustomerId;
    }

    public void setbCustomerId(Long bCustomerId) {
        this.bCustomerId = bCustomerId;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }
}