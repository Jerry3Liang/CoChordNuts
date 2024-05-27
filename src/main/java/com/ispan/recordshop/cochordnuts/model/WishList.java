package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="wish_list")
public class WishList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wishListNo;//流水號
	
	@ManyToOne
	@JoinColumn(
			name = "productNo",
			referencedColumnName = "productNo")
	private Product product;
	
	@ManyToOne
	@JoinColumn(
			name = "memberNo",
			referencedColumnName = "memberNo")
	private Member member;
	
	public WishList() {
	}
	

	@Override
	public String toString() {
		return "WishList [wishListNo=" + wishListNo + ", product=" + product + ", member=" + member + "]";
	}


	public Integer getWishListNo() {
		return wishListNo;
	}

	public void setWishListNo(Integer wishListNo) {
		this.wishListNo = wishListNo;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
	

}
