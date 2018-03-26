package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.ReferralRelation")
public class ReferralRelationModel{
	
	private Long id;
	private Long doctorId;
	private Long customerId;
	private Integer status;
		
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
		
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Long getCustomerId(){
		return this.customerId;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
		
}