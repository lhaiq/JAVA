package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.entity.Appointment")
public class AppointmentModel{

	public static final int SOURCE_FROTEND=0;
	public static final int SOURCE_BACKEDN=1;


	private Long id;
	private Long doctorId;
	private String doctorName;
	private Long doctorConfigId;
	private Integer timeBucket;
	private Long itemClassId;
	private Long customerId;
	private String customerName;
	private Long patientId;
	private String patientName;
	private String time;
	private Integer type;
	private Integer status;
	private String remark;
	private String cancelReason;
	private String appointId;
	private Integer source;
	private Long hospitalId;
	private String hospitalName;
	private Integer timeCost;
	private String visitPlans;
	private Date createTime;
	private String snapshot;
	private String address;

	public static int getSourceFrotend() {
		return SOURCE_FROTEND;
	}

	public static int getSourceBackedn() {
		return SOURCE_BACKEDN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Long getDoctorConfigId() {
		return doctorConfigId;
	}

	public void setDoctorConfigId(Long doctorConfigId) {
		this.doctorConfigId = doctorConfigId;
	}

	public Integer getTimeBucket() {
		return timeBucket;
	}

	public void setTimeBucket(Integer timeBucket) {
		this.timeBucket = timeBucket;
	}

	public Long getItemClassId() {
		return itemClassId;
	}

	public void setItemClassId(Long itemClassId) {
		this.itemClassId = itemClassId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getAppointId() {
		return appointId;
	}

	public void setAppointId(String appointId) {
		this.appointId = appointId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Integer getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(Integer timeCost) {
		this.timeCost = timeCost;
	}

	public String getVisitPlans() {
		return visitPlans;
	}

	public void setVisitPlans(String visitPlans) {
		this.visitPlans = visitPlans;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}