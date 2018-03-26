package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.DoctorConfig")
public class DoctorConfigModel{
	
	private Long id;
	private Long doctorId;
	private Long hospitalId;
	private String  hospitalName;
	private String date;
	private Integer dayOfWeek;
	private Integer interval;


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String startTime;
	private String endTime;
	private Integer status;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setDoctorId(Long doctorId){
		this.doctorId = doctorId;
	}
	
	public Long getDoctorId(){
		return this.doctorId;
	}
		
	public void setHospitalId(Long hospitalId){
		this.hospitalId = hospitalId;
	}
	
	public Long getHospitalId(){
		return this.hospitalId;
	}
		
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return this.date;
	}
		
	public void setDayOfWeek(Integer dayOfWeek){
		this.dayOfWeek = dayOfWeek;
	}
	
	public Integer getDayOfWeek(){
		return this.dayOfWeek;
	}
		
	public void setInterval(Integer interval){
		this.interval = interval;
	}
	
	public Integer getInterval(){
		return this.interval;
	}

	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalName() {
		return hospitalName;
	}
}