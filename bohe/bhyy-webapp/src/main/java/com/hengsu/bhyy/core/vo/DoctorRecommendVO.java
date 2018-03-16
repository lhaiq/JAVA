package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.DoctorRecommendModel")
public class DoctorRecommendVO{
	
	private Long id;
	private Long presenter;
	private Long presentee;
	private Date updateTime;
	private Integer rank;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setPresenter(Long presenter){
		this.presenter = presenter;
	}
	
	public Long getPresenter(){
		return this.presenter;
	}
		
	public void setPresentee(Long presentee){
		this.presentee = presentee;
	}
	
	public Long getPresentee(){
		return this.presentee;
	}
		
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime(){
		return this.updateTime;
	}
		
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getRank(){
		return this.rank;
	}
		
		
}