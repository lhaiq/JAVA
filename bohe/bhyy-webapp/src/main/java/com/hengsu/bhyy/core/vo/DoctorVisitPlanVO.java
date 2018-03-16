package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.model.DoctorVisitPlanModel")
public class DoctorVisitPlanVO{
	
	private Long id;
	private Long configId;
	private String time;
	private Integer timeRange;
	private Integer status;
	private String date;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setConfigId(Long configId){
		this.configId = configId;
	}
	
	public Long getConfigId(){
		return this.configId;
	}
		
	public void setTime(String time){
		this.time = time;
	}
	
	public String getTime(){
		return this.time;
	}
		
	public void setTimeRange(Integer timeRange){
		this.timeRange = timeRange;
	}
	
	public Integer getTimeRange(){
		return this.timeRange;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return this.date;
	}
		
		
}