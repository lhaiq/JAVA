package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.ItemClass")
public class ItemClassModel{
	
	private Long id;
	private String className;
	private Date createTime;
	private Integer type;
	private Integer status;
	private Integer rank;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setClassName(String className){
		this.className = className;
	}
	
	public String getClassName(){
		return this.className;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getRank(){
		return this.rank;
	}
		
		
}