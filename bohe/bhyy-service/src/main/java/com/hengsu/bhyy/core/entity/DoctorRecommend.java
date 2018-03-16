package com.hengsu.bhyy.core.entity;

import java.util.Date;

public class DoctorRecommend {
    private Long id;

    private Long presenter;

    private Long presentee;

    private Date updateTime;

    private Integer rank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPresenter() {
        return presenter;
    }

    public void setPresenter(Long presenter) {
        this.presenter = presenter;
    }

    public Long getPresentee() {
        return presentee;
    }

    public void setPresentee(Long presentee) {
        this.presentee = presentee;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}