package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.CaseReportModel")
public class CaseReportVO{
	
	private Long id;
	private Long appointId;
	private String appointStrId;
	private Long billId;
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private Long patientId;
	private Long doctorId;
	private String doctorName;
	private String doctorPhone;
	private Long categoryId;
	private Long firstCategoryId;
	private Long secondCategoryId;
	private Integer type;
	private Integer status;
	private Integer caseType;
	private Date createTime;
	private Integer isSend;
	private String time;
	private Long hospitalId;
	private String hospitalName;
	private String content;


	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getDoctorPhone() {
		return doctorPhone;
	}

	public void setDoctorPhone(String doctorPhone) {
		this.doctorPhone = doctorPhone;
	}

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
		
	public void setBillId(Long billId){
		this.billId = billId;
	}
	
	public Long getBillId(){
		return this.billId;
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
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setIsSend(Integer isSend){
		this.isSend = isSend;
	}
	
	public Integer getIsSend(){
		return this.isSend;
	}
		
	public void setTime(String time){
		this.time = time;
	}
	
	public String getTime(){
		return this.time;
	}
		
	public void setHospitalId(Long hospitalId){
		this.hospitalId = hospitalId;
	}
	
	public Long getHospitalId(){
		return this.hospitalId;
	}
		
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Long getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Long firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public Long getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(Long secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public void setAppointStrId(String appointStrId) {
		this.appointStrId = appointStrId;
	}

	public String getAppointStrId() {
		return appointStrId;
	}
}