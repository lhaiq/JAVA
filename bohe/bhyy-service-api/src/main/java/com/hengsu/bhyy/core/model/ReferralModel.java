package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.Referral")
public class ReferralModel{
	
	private Long id;
	private Long customerId;
	private Long doctorId;
	private Integer status;
	private Integer type;
	private Integer num;
	private Date operationTime;
	private Integer isSend;
	private Long appointId;
	private Long billId;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Long getCustomerId(){
		return this.customerId;
	}
		
	public void setDoctorId(Long doctorId){
		this.doctorId = doctorId;
	}
	
	public Long getDoctorId(){
		return this.doctorId;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Integer getNum(){
		return this.num;
	}
		
	public void setOperationTime(Date operationTime){
		this.operationTime = operationTime;
	}
	
	public Date getOperationTime(){
		return this.operationTime;
	}
		
	public void setIsSend(Integer isSend){
		this.isSend = isSend;
	}
	
	public Integer getIsSend(){
		return this.isSend;
	}
		
	public void setAppointId(Long appointId){
		this.appointId = appointId;
	}
	
	public Long getAppointId(){
		return this.appointId;
	}
		
	public void setBillId(Long billId){
		this.billId = billId;
	}
	
	public Long getBillId(){
		return this.billId;
	}
		
		
}