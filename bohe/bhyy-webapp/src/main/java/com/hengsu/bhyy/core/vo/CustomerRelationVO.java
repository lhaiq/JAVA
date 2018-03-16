package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.model.CustomerRelationModel")
public class CustomerRelationVO{
	
	private Long id;
	private Long aCustomerId;
	private Long bCustomerId;
	private Integer relation;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setACustomerId(Long aCustomerId){
		this.aCustomerId = aCustomerId;
	}
	
	public Long getACustomerId(){
		return this.aCustomerId;
	}
		
	public void setBCustomerId(Long bCustomerId){
		this.bCustomerId = bCustomerId;
	}
	
	public Long getBCustomerId(){
		return this.bCustomerId;
	}
		
	public void setRelation(Integer relation){
		this.relation = relation;
	}
	
	public Integer getRelation(){
		return this.relation;
	}
		
		
}