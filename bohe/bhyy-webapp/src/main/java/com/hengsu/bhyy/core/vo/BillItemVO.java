package com.hengsu.bhyy.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.math.BigDecimal;

@MapClass("com.hengsu.bhyy.core.model.BillItemModel")
public class BillItemVO{
	
	private Long id;
	private Long billId;
	private Long itemId;
	private String itemName;
	private Integer num;
	private BigDecimal discount;
	private BigDecimal originalPrice;
	private BigDecimal actualCost;
		
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
		
	public void setItemId(Long itemId){
		this.itemId = itemId;
	}
	
	public Long getItemId(){
		return this.itemId;
	}
		
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	
	public String getItemName(){
		return this.itemName;
	}
		
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Integer getNum(){
		return this.num;
	}
		
	public void setDiscount(BigDecimal discount){
		this.discount = discount;
	}
	
	public BigDecimal getDiscount(){
		return this.discount;
	}
		
	public void setOriginalPrice(BigDecimal originalPrice){
		this.originalPrice = originalPrice;
	}
	
	public BigDecimal getOriginalPrice(){
		return this.originalPrice;
	}
		
	public void setActualCost(BigDecimal actualCost){
		this.actualCost = actualCost;
	}
	
	public BigDecimal getActualCost(){
		return this.actualCost;
	}
		
		
}