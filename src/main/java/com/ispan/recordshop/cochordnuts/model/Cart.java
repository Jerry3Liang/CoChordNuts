package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Cart")
public class Cart {

	public Cart() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartNo")
	private Integer cartNo;
	
	@Column(name = "memberNo")
	private Integer memberNo;
	
	@Column(name = "productNo")
	private Integer productNo;
	
	@Column(name = "count")
	private Integer count;

	public Integer getCartNo() {
		return cartNo;
	}

	public void setCartNo(Integer cartNo) {
		this.cartNo = cartNo;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getProductNo() {
		return productNo;
	}

	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	
}
