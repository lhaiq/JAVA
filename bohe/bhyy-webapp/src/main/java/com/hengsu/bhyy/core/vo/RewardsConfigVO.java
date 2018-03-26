package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.math.BigDecimal;

@MapClass("com.hengsu.bhyy.core.model.RewardsConfigModel")
public class RewardsConfigVO{
	
	private Long id;
	private String keyStr;
	private Integer type;
	private BigDecimal num;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setKeyStr(String keyStr){
		this.keyStr = keyStr;
	}
	
	public String getKeyStr(){
		return this.keyStr;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
	public void setNum(BigDecimal num){
		this.num = num;
	}
	
	public BigDecimal getNum(){
		return this.num;
	}
		
		
}