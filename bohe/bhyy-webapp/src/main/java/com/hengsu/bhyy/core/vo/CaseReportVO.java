package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.CaseReportModel")
public class CaseReportVO{
	
	private Long id;
	private Long appointId;
	private Long customerId;
	private Long patientId;
	private Long doctorId;
	private Long itemClassId;
	private Long categoryId;
	private Integer type;
	private Integer status;
	private Integer caseType;
	private Integer itemType;
	private Date createTime;
	private String content;
		
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
		
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Long getCustomerId(){
		return this.customerId;
	}
		
	public void setPatientId(Long patientId){
		this.patientId = patientId;
	}
	
	public Long getPatientId(){
		return this.patientId;
	}
		
	public void setDoctorId(Long doctorId){
		this.doctorId = doctorId;
	}
	
	public Long getDoctorId(){
		return this.doctorId;
	}
		
	public void setItemClassId(Long itemClassId){
		this.itemClassId = itemClassId;
	}
	
	public Long getItemClassId(){
		return this.itemClassId;
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
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setCaseType(Integer caseType){
		this.caseType = caseType;
	}
	
	public Integer getCaseType(){
		return this.caseType;
	}
		
	public void setItemType(Integer itemType){
		this.itemType = itemType;
	}
	
	public Integer getItemType(){
		return this.itemType;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
		
		
}