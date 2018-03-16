package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.CustomerRelation")
public class CustomerRelationModel{
	
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
		

	public void setRelation(Integer relation){
		this.relation = relation;
	}
	
	public Integer getRelation(){
		return this.relation;
	}


	public void setaCustomerId(Long aCustomerId) {
		this.aCustomerId = aCustomerId;
	}

	public Long getaCustomerId() {
		return aCustomerId;
	}

	public void setbCustomerId(Long bCustomerId) {
		this.bCustomerId = bCustomerId;
	}

	public Long getbCustomerId() {
		return bCustomerId;
	}
}