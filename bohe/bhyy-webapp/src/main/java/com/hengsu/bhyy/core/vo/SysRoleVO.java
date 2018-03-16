package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.util.List;

@MapClass("com.hengsu.bhyy.core.model.SysRoleModel")
public class SysRoleVO{
	
	private Long id;
	private String name;
	private Date createTime;
	private List<Long> menuIds;
		
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
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}


	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}
}