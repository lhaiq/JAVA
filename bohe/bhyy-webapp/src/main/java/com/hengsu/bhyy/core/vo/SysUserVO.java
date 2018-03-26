package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.util.List;

@MapClass("com.hengsu.bhyy.core.model.SysUserModel")
public class SysUserVO{
	
	private Long id;
	private String username;
	private String realName;
	private String phone;
	private String password;
	private Date createTime;
	private Integer enable;
	private Long roleId;
	private String roleName;
	private String sessionId;


	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public String getRealName(){
		return this.realName;
	}
		
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return this.phone;
	}
		
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setEnable(Integer enable){
		this.enable = enable;
	}
	
	public Integer getEnable(){
		return this.enable;
	}



	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}
}