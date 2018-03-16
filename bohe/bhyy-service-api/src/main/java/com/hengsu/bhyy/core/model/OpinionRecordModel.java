package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.OpinionRecord")
public class OpinionRecordModel{
	
	private Long id;
	private Long opinionId;
	private String content;
	private Integer type;
	private Long managerId;
	private Date createTime;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setOpinionId(Long opinionId){
		this.opinionId = opinionId;
	}
	
	public Long getOpinionId(){
		return this.opinionId;
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
		
		
}