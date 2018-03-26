package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import scala.Int;

import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.NotifyModel")
public class NotifyVO{
	
	private Long id;
	private Integer type;
	private Long customerId;
	private Integer status;
	private Date updateTime;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
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
		
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime(){
		return this.updateTime;
	}
		
		
}