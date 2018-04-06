package com.hengsu.bhyy.core.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Doctor {
    private Long id;

    private String realName;

    private String phone;

    private String icon;

    private Integer gender;

    private Date birthday;

    private String hospitalName;

    private String department;

    private String title;

    private String adept;

    private String education;

    private Integer workYear;

    private String intro;

    private String idIcon;

    private String jobIcon1;

    private String jobIcon2;

    private Date addDate;

    private String doctorIcon;

    private Integer status;
    private BigDecimal balance;

    private Integer age;

    private String serviceItem;

    private Integer isShow;

    private Integer source;

    private String failureReason;

    private Integer isSend;

    private Long inviteId;


    private Integer rank;

    private String assistantQr;

    private Date failureTime;

    private String openId;

    private String assistantName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setServiceItem(String serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(String idIcon) {
        this.idIcon = idIcon;
    }

    public String getJobIcon1() {
        return jobIcon1;
    }

    public void setJobIcon1(String jobIcon1) {
        this.jobIcon1 = jobIcon1;
    }

    public String getJobIcon2() {
        return jobIcon2;
    }

    public void setJobIcon2(String jobIcon2) {
        this.jobIcon2 = jobIcon2;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getDoctorIcon() {
        return doctorIcon;
    }

    public void setDoctorIcon(String doctorIcon) {
        this.doctorIcon = doctorIcon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getServiceItem() {
        return serviceItem;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }

    public Long getInviteId() {
        return inviteId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setAssistantQr(String assistantQr) {
        this.assistantQr = assistantQr;
    }

    public String getAssistantQr() {
        return assistantQr;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public BigDecimal getBalance() {
        return balance;
    }
}