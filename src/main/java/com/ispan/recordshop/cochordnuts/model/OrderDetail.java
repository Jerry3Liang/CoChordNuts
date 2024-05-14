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
		
	private Integer productTotalPay;//小計(單價*數量)
	
	private Double discount;//打折數
	
	private Integer productBoughtCount;//數量
	
	@ManyToOne
	@JoinColumn(name="orderNo" ,referencedColumnName = "orderNo")
	private Orders orderNo;//訂單編號
		
	@ManyToOne //與Product連接
	@JoinColumn(name="productNo" ,referencedColumnName = "productNo")
	private Product productNo;//商品編號
	
	public OrderDetail() {

	}

	public Integer getOrderDetailNo() {
		return orderDetailNo;
	}

	public void setOrderDetailNo(Integer orderDetailNo) {
		this.orderDetailNo = orderDetailNo;
	}

	public Integer getProductTotalPay() {		
		return productTotalPay;
	}

	public void setProductTotalPay(Integer productTotalPay) {//單一商品小計
		productTotalPay=productNo.getUnitPrice()*productBoughtCount;
		this.productTotalPay = productTotalPay;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount() {
		this.discount = productNo.getDiscount();
	}

	public Integer getProductBoughtCount() {
		return productBoughtCount;
	}

	public void setProductBoughtCount(Integer productBoughtCount) {
		this.productBoughtCount = productBoughtCount;
	}

	public Orders getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Orders orderNo) {
		this.orderNo = orderNo;
	}

	public Product getProductNo() {
		return productNo;
	}

	public void setProductNo(Product productNo) {
		this.productNo = productNo;
	}

	
	
	


}
