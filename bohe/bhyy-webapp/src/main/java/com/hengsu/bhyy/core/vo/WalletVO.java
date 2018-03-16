package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.math.BigDecimal;

@MapClass("com.hengsu.bhyy.core.model.WalletModel")
public class WalletVO{
	
	private Long id;
	private Long doctorId;
	private Long billId;
	private String name;
	private BigDecimal money;
	private Date createTime;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setDoctorId(Long doctorId){
		this.doctorId = doctorId;
	}
	
	public Long getDoctorId(){
		return this.doctorId;
	}
		
	public void setBillId(Long billId){
		this.billId = billId;
	}
	
	public Long getBillId(){
		return this.billId;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
		
	public void setMoney(BigDecimal money){
		this.money = money;
	}
	
	public BigDecimal getMoney(){
		return this.money;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
		
}