package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.SysRoleMenu")
public class SysRoleMenuModel{
	
	private Long id;
	private Long roleId;
	private Long menuId;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}
	
	public Long getRoleId(){
		return this.roleId;
	}
		
	public void setMenuId(Long menuId){
		this.menuId = menuId;
	}
	
	public Long getMenuId(){
		return this.menuId;
	}
		
		
}