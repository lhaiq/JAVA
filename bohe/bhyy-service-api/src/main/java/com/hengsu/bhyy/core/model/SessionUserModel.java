package com.hengsu.bhyy.core.model;

/**
 * Created by haiquan.li on 18/3/4.
 */
public class SessionUserModel {

    public static final int ROLE_PATIENT=0;
    public static final int ROLE_DOCTOR=1;
    public static final int ROLE_ADMIN=2;

    private Long userId;
    private int role;//0-患者 1-医生 2-后台管理员


    public SessionUserModel(Long userId,int role){
        this.userId=userId;
        this.role=role;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
