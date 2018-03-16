package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.SysUserRole")
public class SysUserRoleModel{
	
	private Long id;
	private Long userId;
	private Long roleId;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getUserId(){
		return this.userId;
	}
		
	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}
	
	public Long getRoleId(){
		return this.roleId;
	}
		
		
}