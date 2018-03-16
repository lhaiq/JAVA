package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.math.BigDecimal;

@MapClass("com.hengsu.bhyy.core.entity.Customer")
public class CustomerModel{

	public static final int TYPE_REGISTER=0;
	public static final int TYPE_RELATION=1;

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
		
	public void setPayMoney(BigDecimal payMoney){
		this.payMoney = payMoney;
	}
	
	public BigDecimal getPayMoney(){
		return this.payMoney;
	}
		
	public void setOrg(String org){
		this.org = org;
	}
	
	public String getOrg(){
		return this.org;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}


	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public Integer getRelation() {
		return relation;
	}
}