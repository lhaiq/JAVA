package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.CaseTemplate")
public class CaseTemplateModel{
	
	private Long id;
	private Long categoryId;
	private Integer type;
	private String content;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setCategoryId(Long categoryId){
		this.categoryId = categoryId;
	}
	
	public Long getCategoryId(){
		return this.categoryId;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
		
		
}