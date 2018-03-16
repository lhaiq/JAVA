package com.hengsu.bhyy.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.hengsu.bhyy.core.entity.Image")
public class ImageModel{
	
	private Long id;
	private String fileName;
	private String path;
	private String extension;
	private String type;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return this.fileName;
	}
		
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return this.path;
	}
		
	public void setExtension(String extension){
		this.extension = extension;
	}
	
	public String getExtension(){
		return this.extension;
	}
		
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
		
		
}