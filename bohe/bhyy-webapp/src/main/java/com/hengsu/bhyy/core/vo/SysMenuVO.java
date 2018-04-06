package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.List;

@MapClass("com.hengsu.bhyy.core.model.SysMenuModel")
public class SysMenuVO{
	
	private Long id;
	private String name;
	private String url;
	private Long parentId;
	private Integer rank;
	private String icon;
	private String key;
	private String controller;
	private List<SysMenuVO> children;
		
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
		
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return this.url;
	}
		
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	
	public Long getParentId(){
		return this.parentId;
	}
		
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	public Integer getRank(){
		return this.rank;
	}
		
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public String getIcon(){
		return this.icon;
	}
		
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey(){
		return this.key;
	}
		
	public void setController(String controller){
		this.controller = controller;
	}
	
	public String getController(){
		return this.controller;
	}


	public void setChildren(List<SysMenuVO> children) {
		this.children = children;
	}

	public List<SysMenuVO> getChildren() {
		return children;
	}
}