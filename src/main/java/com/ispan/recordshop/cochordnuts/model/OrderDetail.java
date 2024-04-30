package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="order_detail")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailNo;//訂單明細流水號
	
	private Integer orderNo;//訂單編號
	
	private String productName;//名稱
	
	private Integer count;//數量
	
	private Integer unitPrice;//單價
	
	private Integer pay;//小計(單價*數量)
	
	private float discount;//打折數
	
	private Integer productNo;//商品編號
	
	public OrderDetail() {

	}
	
	public Integer getOrderDetailNo() {
		return orderDetailNo;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public String getProductName() {
		return productName;
	}

	public Integer getCount() {
		return count;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public Integer getPay() {
		return pay;
	}

	public float getDiscount() {
		return discount;
	}

	public Integer getProductNo() {
		return productNo;
	}

	public void setOrderDetailNo(Integer orderDetailNo) {
		this.orderDetailNo = orderDetailNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}
	

	

}
