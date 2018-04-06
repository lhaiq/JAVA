package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.hengsu.bhyy.core.model.HospitalModel")
public class HospitalVO{
	
	private Long id;
	private String name;
	private String brand;
	private String prov;
	private String city;
	private String area;
	private String telephone;
	private String address;
	private String route;
	private Integer toothChairNum;
	private String leader;
	private String leaderPhone;
	private String boheJoin;
	private Date establishDate;
	private Integer staffNum;
	private String password;
	private String hosQualificat;
	private String eiaQualificat;
	private String account;
	private Integer isEnable;
	private Date createTime;
	private Integer rank;
	private String img1;

	private String img2;

	private String img3;

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
		
	public void setBrand(String brand){
		this.brand = brand;
	}
	
	public String getBrand(){
		return this.brand;
	}
		
	public void setProv(String prov){
		this.prov = prov;
	}
	
	public String getProv(){
		return this.prov;
	}
		
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return this.city;
	}
		
	public void setArea(String area){
		this.area = area;
	}
	
	public String getArea(){
		return this.area;
	}
		
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	
	public String getTelephone(){
		return this.telephone;
	}
		
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return this.address;
	}
		
	public void setRoute(String route){
		this.route = route;
	}
	
	public String getRoute(){
		return this.route;
	}
		
	public void setToothChairNum(Integer toothChairNum){
		this.toothChairNum = toothChairNum;
	}
	
	public Integer getToothChairNum(){
		return this.toothChairNum;
	}
		
	public void setLeader(String leader){
		this.leader = leader;
	}
	
	public String getLeader(){
		return this.leader;
	}
		
	public void setLeaderPhone(String leaderPhone){
		this.leaderPhone = leaderPhone;
	}
	
	public String getLeaderPhone(){
		return this.leaderPhone;
	}
		
	public void setBoheJoin(String boheJoin){
		this.boheJoin = boheJoin;
	}
	
	public String getBoheJoin(){
		return this.boheJoin;
	}
		
	public void setEstablishDate(Date establishDate){
		this.establishDate = establishDate;
	}
	
	public Date getEstablishDate(){
		return this.establishDate;
	}
		
	public void setStaffNum(Integer staffNum){
		this.staffNum = staffNum;
	}
	
	public Integer getStaffNum(){
		return this.staffNum;
	}
		
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
		
	public void setHosQualificat(String hosQualificat){
		this.hosQualificat = hosQualificat;
	}
	
	public String getHosQualificat(){
		return this.hosQualificat;
	}
		
	public void setEiaQualificat(String eiaQualificat){
		this.eiaQualificat = eiaQualificat;
	}
	
	public String getEiaQualificat(){
		return this.eiaQualificat;
	}
		
	public void setAccount(String account){
		this.account = account;
	}
	
	public String getAccount(){
		return this.account;
	}
		
	public void setIsEnable(Integer isEnable){
		this.isEnable = isEnable;
	}
	
	public Integer getIsEnable(){
		return this.isEnable;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getRank(){
		return this.rank;
	}
		
		
}