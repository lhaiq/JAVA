package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.NotifyMessage")
public class NotifyMessageModel{
	
	private Long id;
	private Long doctorId;
	private Long configId;
	private String message;
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
		
	public void setConfigId(Long configId){
		this.configId = configId;
	}
	
	public Long getConfigId(){
		return this.configId;
	}
		
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
		
}