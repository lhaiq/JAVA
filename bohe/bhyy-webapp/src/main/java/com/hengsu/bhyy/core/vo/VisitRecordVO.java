package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.VisitRecordModel")
public class VisitRecordVO{
	
	private Long id;
	private Long customerId;
	private String content;
	private Integer type;
	private Long managerId;
	private Date createTime;
	private Long appointId;
		
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
		
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
	public void setManagerId(Long managerId){
		this.managerId = managerId;
	}
	
	public Long getManagerId(){
		return this.managerId;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setAppointId(Long appointId){
		this.appointId = appointId;
	}
	
	public Long getAppointId(){
		return this.appointId;
	}
		
		
}