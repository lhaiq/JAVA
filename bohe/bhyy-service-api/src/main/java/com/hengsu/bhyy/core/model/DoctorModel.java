package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.Doctor")
public class DoctorModel{

	public static final int UN_CHECK=0;
	public static final int PASS=1;
	public static final int REFUSE=2;


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
	private Integer age;
	private String serviceItem;
	private Integer isShow;
	private Integer source;
	private String failureReason;
	private Integer isRecommend;
	private String inviteCode;
	private Long recommendId;
	private Integer rank;
	private String assistantQr;
	private BigDecimal balance;
	private Date failureTime;

	public void setFailureTime(Date failureTime) {
		this.failureTime = failureTime;
	}

	public Date getFailureTime() {
		return failureTime;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public String getRealName(){
		return this.realName;
	}
		
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return this.phone;
	}
		
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public String getIcon(){
		return this.icon;
	}
		
	public void setGender(Integer gender){
		this.gender = gender;
	}
	
	public Integer getGender(){
		return this.gender;
	}
		
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	
	public Date getBirthday(){
		return this.birthday;
	}
		
	public void setHospitalName(String hospitalName){
		this.hospitalName = hospitalName;
	}
	
	public String getHospitalName(){
		return this.hospitalName;
	}
		

	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
		
	public void setAdept(String adept){
		this.adept = adept;
	}
	
	public String getAdept(){
		return this.adept;
	}
		
	public void setEducation(String education){
		this.education = education;
	}
	
	public String getEducation(){
		return this.education;
	}
		
	public void setWorkYear(Integer workYear){
		this.workYear = workYear;
	}
	
	public Integer getWorkYear(){
		return this.workYear;
	}
		
	public void setIntro(String intro){
		this.intro = intro;
	}
	
	public String getIntro(){
		return this.intro;
	}
		
	public void setIdIcon(String idIcon){
		this.idIcon = idIcon;
	}
	
	public String getIdIcon(){
		return this.idIcon;
	}
		
	public void setJobIcon1(String jobIcon1){
		this.jobIcon1 = jobIcon1;
	}
	
	public String getJobIcon1(){
		return this.jobIcon1;
	}
		
	public void setJobIcon2(String jobIcon2){
		this.jobIcon2 = jobIcon2;
	}
	
	public String getJobIcon2(){
		return this.jobIcon2;
	}
		
	public void setAddDate(Date addDate){
		this.addDate = addDate;
	}
	
	public Date getAddDate(){
		return this.addDate;
	}
		
	public void setDoctorIcon(String doctorIcon){
		this.doctorIcon = doctorIcon;
	}
	
	public String getDoctorIcon(){
		return this.doctorIcon;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setAge(Integer age){
		this.age = age;
	}
	
	public Integer getAge(){
		return this.age;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getServiceItem() {
		return serviceItem;
	}


	public String getDepartment() {
		return department;
	}

	public void setIsShow(Integer isShow){
		this.isShow = isShow;
	}
	
	public Integer getIsShow(){
		return this.isShow;
	}
		
	public void setSource(Integer source){
		this.source = source;
	}
	
	public Integer getSource(){
		return this.source;
	}
		
	public void setFailureReason(String failureReason){
		this.failureReason = failureReason;
	}
	
	public String getFailureReason(){
		return this.failureReason;
	}
		
	public void setIsRecommend(Integer isRecommend){
		this.isRecommend = isRecommend;
	}
	
	public Integer getIsRecommend(){
		return this.isRecommend;
	}
		
	public void setInviteCode(String inviteCode){
		this.inviteCode = inviteCode;
	}
	
	public String getInviteCode(){
		return this.inviteCode;
	}
		
	public void setRecommendId(Long recommendId){
		this.recommendId = recommendId;
	}
	
	public Long getRecommendId(){
		return this.recommendId;
	}
		
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getRank(){
		return this.rank;
	}

	public void setAssistantQr(String assistantQr) {
		this.assistantQr = assistantQr;
	}


	public String getAssistantQr() {
		return assistantQr;
	}
}