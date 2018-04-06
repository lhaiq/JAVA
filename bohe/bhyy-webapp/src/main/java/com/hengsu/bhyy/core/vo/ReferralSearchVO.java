package com.hengsu.bhyy.core.vo;

import java.util.List;

/**
 * Created by haiquan.li on 18/4/6.
 */
public class ReferralSearchVO {

    private List<Integer> statuses;
    private String startDate;
    private String endDate;


    public List<Integer> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Integer> statuses) {
        this.statuses = statuses;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
