package com.ispan.recordshop.cochordnuts.dto;

public class CartForOrdersDto {
	private Integer count;
	private Integer unitPrice;
	private Double discount;
	private Long total;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal() {
		Double d =unitPrice*count*discount;
		this.total = (Long)Math.round(d);
	}
	
	
	
	

}
