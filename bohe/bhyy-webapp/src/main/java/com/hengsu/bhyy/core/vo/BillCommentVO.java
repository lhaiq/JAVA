package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.util.List;

@MapClass("com.hengsu.bhyy.core.model.BillCommentModel")
public class BillCommentVO{
	
	private Long id;
	private Long billId;
	private String tags;
	private String content;
	private Date createTime;
	private List<Long> tagIds;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setBillId(Long billId){
		this.billId = billId;
	}
	
	public Long getBillId(){
		return this.billId;
	}
		
	public void setTags(String tags){
		this.tags = tags;
	}
	
	public String getTags(){
		return this.tags;
	}
		
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}


	public void setTagIds(List<Long> tagIds) {
		this.tagIds = tagIds;
	}

	public List<Long> getTagIds() {
		return tagIds;
	}
}