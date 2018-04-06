package com.hengsu.bhyy.core.vo;

import java.util.List;

/**
 * Created by haiquan.li on 18/3/21.
 */
public class DoctorSearchVO {

    private List<Long> dayOfWeek;
    private String name;
    private String itemName;
    private Integer isRecommend;


    public List<Long> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<Long> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }
}
