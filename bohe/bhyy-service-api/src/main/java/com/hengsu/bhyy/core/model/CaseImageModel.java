package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.CaseImage")
public class CaseImageModel{
	
	private Long id;
	private Long appointId;
	private Date filmTime;
	private String path;
	private Date createTime;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setAppointId(Long appointId){
		this.appointId = appointId;
	}
	
	public Long getAppointId(){
		return this.appointId;
	}
		
	public void setFilmTime(Date filmTime){
		this.filmTime = filmTime;
	}
	
	public Date getFilmTime(){
		return this.filmTime;
	}
		
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return this.path;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
		
}