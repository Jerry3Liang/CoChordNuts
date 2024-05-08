package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orderDetail")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailNo;//訂單明細流水號
	
	@ManyToOne
	@JoinColumn(name="orderNo" ,referencedColumnName = "orderNo")
	private Orders orderNo;//訂單編號
	
	private String productName;//名稱
	
	private Integer count;//數量
	
	private Integer unitPrice;//單價
	
	private Integer pay;//小計(單價*數量)
	
	private float discount;//打折數
	
	@ManyToOne //與Product連接
	@JoinColumn(name="productNo" ,referencedColumnName = "productNo")
	private Product productNo;//商品編號
//	private Integer productNo;//商品編號
	
	public OrderDetail() {

	}

	public Integer getOrderDetailNo() {
		return orderDetailNo;
	}

	public void setOrderDetailNo(Integer orderDetailNo) {
		this.orderDetailNo = orderDetailNo;
	}

	public Orders getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Orders orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

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

	public Integer getPay() {
		return pay;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Product getProductNo() {
		return productNo;
	}

	public void setProductNo(Product productNo) {
		this.productNo = productNo;
	}

//	public Integer getProductNo() {
//		return productNo;
//	}
//
//	public void setProductNo(Integer productNo) {
//		this.productNo = productNo;
//	}
	
	
	
	


}
