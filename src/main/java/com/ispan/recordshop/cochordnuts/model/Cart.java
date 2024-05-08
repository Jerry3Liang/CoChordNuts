package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="Cart")
public class Cart {

	public Cart() {
		// TODO Auto-generated constructor stub
	}
	@EmbeddedId
	private CartId cartId;

	private Integer Count;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@MapsId("memberId")
	private Member member;
	
	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public CartId getCartId() {
		return cartId;
	}

	public void setCartId(CartId cartId) {
		this.cartId = cartId;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	//public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	//public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
