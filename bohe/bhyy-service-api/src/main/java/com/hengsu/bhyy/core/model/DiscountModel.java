package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.math.BigDecimal;

@MapClass("com.hengsu.bhyy.core.entity.Discount")
public class DiscountModel{
	
	private Long id;
	private BigDecimal limitPercentage;
	private Date createTime;
	private Integer status;
	private Integer rank;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setLimitPercentage(BigDecimal limitPercentage){
		this.limitPercentage = limitPercentage;
	}
	
	public BigDecimal getLimitPercentage(){
		return this.limitPercentage;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
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