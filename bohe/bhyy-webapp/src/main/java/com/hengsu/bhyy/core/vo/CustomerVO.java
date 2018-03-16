package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.CustomerModel")
public class CustomerVO{
	
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
	private int relation;
	private String sessionId;

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public int getRelation() {
		return relation;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	
	public String getNickName(){
		return this.nickName;
	}
		
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public String getRealName(){
		return this.realName;
	}
		
	public void setGender(Integer gender){
		this.gender = gender;
	}
	
	public Integer getGender(){
		return this.gender;
	}
		
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return this.phone;
	}
		
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	
	public Date getBirthday(){
		return this.birthday;
	}
		
	public void setAge(Integer age){
		this.age = age;
	}
	
	public Integer getAge(){
		return this.age;
	}
		
	public void setSource(Integer source){
		this.source = source;
	}
	
	public Integer getSource(){
		return this.source;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public String getIcon(){
		return this.icon;
	}
		
	public void setAppointNum(Integer appointNum){
		this.appointNum = appointNum;
	}
	
	public Integer getAppointNum(){
		return this.appointNum;
	}
		
	public void setTreatNum(Integer treatNum){
		this.treatNum = treatNum;
	}
	
	public Integer getTreatNum(){
		return this.treatNum;
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

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
}