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
	
	@Override
	public String toString() {
		return "OrderDetail [orderDetailNo=" + orderDetailNo + ", productTotalPay=" + productTotalPay + ", discount="
				+ discount + ", productBoughtCount=" + productBoughtCount + ", orderNo=" + orderNo + ", productNo="
				+ productNo + "]";
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailNo;//訂單明細流水號
		
	private Long productTotalPay;//小計(單價*數量*折扣數)
	
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

	public Long getProductTotalPay() {		
		return productTotalPay;
	}

	public void setProductTotalPay() {//單一商品小計
		Double d =productNo.getUnitPrice()*productBoughtCount*discount;
		Long a =Math.round(d);
		this.productTotalPay=a;
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

//	public Orders getOrderNo() {
//		return orderNo;
//	}
	public Integer getOrderNo() {
		return orderNo.getOrderNo();
	}
	
	
	public void setOrderNo(Orders orderNo) {
		this.orderNo = orderNo;
	}

//	public Product getProductNo() {
//		return productNo;
//	}
	public Integer getProductNo() {
		return productNo.getProductNo();
	}
	
	public String getProductName() {
		return productNo.getProductName();
	}
	
	public Integer getProductUnitPrice() {
		return productNo.getUnitPrice();
	}
	

	public void setProductNo(Product productNo) {
		this.productNo = productNo;
	}
	


	
	
	


}
