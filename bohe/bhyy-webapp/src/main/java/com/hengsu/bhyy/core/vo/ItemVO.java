package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;

@MapClass("com.hengsu.bhyy.core.model.ItemModel")
public class ItemVO{
	
	private Long id;
	private String icon;
	private String unit;
	private BigDecimal price;
	private String doctorClass;
	private String patientClass;
	private String link;
	private String intro;
	private Integer rank;
	private String name;
	private String linkTitle;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public String getIcon(){
		return this.icon;
	}
		
	public void setUnit(String unit){
		this.unit = unit;
	}
	
	public String getUnit(){
		return this.unit;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setDoctorClass(String doctorClass){
		this.doctorClass = doctorClass;
	}
	
	public String getDoctorClass(){
		return this.doctorClass;
	}
		
	public void setPatientClass(String patientClass){
		this.patientClass = patientClass;
	}
	
	public String getPatientClass(){
		return this.patientClass;
	}
		
	public void setLink(String link){
		this.link = link;
	}
	
	public String getLink(){
		return this.link;
	}
		
	public void setIntro(String intro){
		this.intro = intro;
	}
	
	public String getIntro(){
		return this.intro;
	}
		
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getRank(){
		return this.rank;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
		
	public void setLinkTitle(String linkTitle){
		this.linkTitle = linkTitle;
	}
	
	public String getLinkTitle(){
		return this.linkTitle;
	}
		
		
}