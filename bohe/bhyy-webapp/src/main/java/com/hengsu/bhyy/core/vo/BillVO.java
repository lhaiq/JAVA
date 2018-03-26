package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

@MapClass("com.hengsu.bhyy.core.model.BillModel")
public class BillVO{
	
	private Long id;
	private String billId;
	private Long appointmentId;
	private Long customerId;
	private String patientName;
	private Long doctorId;
	private String doctorName;
	private String address;
	private List<String> itemNames;
	private Date createTime;
	private BigDecimal originalCost;
	private BigDecimal discount;
	private BigDecimal actualCost;
	private String hospitalName;
	private BigDecimal couponAmount;
	private Integer isOnSale;
	private Integer payType;
	private Integer isRecheck;
	private Integer recheckDate;
	private String remark;
	private Integer isComment;
	private Integer status;
	private List<BillItemVO> items;
	private BillCommentVO comment;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setBillId(String billId){
		this.billId = billId;
	}
	
	public String getBillId(){
		return this.billId;
	}
		
	public void setAppointmentId(Long appointmentId){
		this.appointmentId = appointmentId;
	}
	
	public Long getAppointmentId(){
		return this.appointmentId;
	}
		
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Long getCustomerId(){
		return this.customerId;
	}
		
	public void setPatientName(String patientName){
		this.patientName = patientName;
	}
	
	public String getPatientName(){
		return this.patientName;
	}
		
	public void setDoctorId(Long doctorId){
		this.doctorId = doctorId;
	}
	
	public Long getDoctorId(){
		return this.doctorId;
	}
		
	public void setDoctorName(String doctorName){
		this.doctorName = doctorName;
	}
	
	public String getDoctorName(){
		return this.doctorName;
	}
		
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return this.address;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setOriginalCost(BigDecimal originalCost){
		this.originalCost = originalCost;
	}
	
	public BigDecimal getOriginalCost(){
		return this.originalCost;
	}
		
	public void setDiscount(BigDecimal discount){
		this.discount = discount;
	}
	
	public BigDecimal getDiscount(){
		return this.discount;
	}
		
	public void setActualCost(BigDecimal actualCost){
		this.actualCost = actualCost;
	}
	
	public BigDecimal getActualCost(){
		return this.actualCost;
	}
		
	public void setHospitalName(String hospitalName){
		this.hospitalName = hospitalName;
	}
	
	public String getHospitalName(){
		return this.hospitalName;
	}
		
	public void setCouponAmount(BigDecimal couponAmount){
		this.couponAmount = couponAmount;
	}
	
	public BigDecimal getCouponAmount(){
		return this.couponAmount;
	}
		
	public void setIsOnSale(Integer isOnSale){
		this.isOnSale = isOnSale;
	}
	
	public Integer getIsOnSale(){
		return this.isOnSale;
	}
		
	public void setPayType(Integer payType){
		this.payType = payType;
	}
	
	public Integer getPayType(){
		return this.payType;
	}
		
	public void setIsRecheck(Integer isRecheck){
		this.isRecheck = isRecheck;
	}
	
	public Integer getIsRecheck(){
		return this.isRecheck;
	}
		
	public void setRecheckDate(Integer recheckDate){
		this.recheckDate = recheckDate;
	}
	
	public Integer getRecheckDate(){
		return this.recheckDate;
	}
		
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	public String getRemark(){
		return this.remark;
	}
		
	public void setIsComment(Integer isComment){
		this.isComment = isComment;
	}
	
	public Integer getIsComment(){
		return this.isComment;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setItems(List<BillItemVO> items) {
		this.items = items;
	}

	public List<BillItemVO> getItems() {
		return items;
	}

	public void setComment(BillCommentVO comment) {
		this.comment = comment;
	}

	public BillCommentVO getComment() {
		return comment;
	}

	public void setItemNames(List<String> itemNames) {
		this.itemNames = itemNames;
	}

	public List<String> getItemNames() {
		return itemNames;
	}
}