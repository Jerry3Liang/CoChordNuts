
package com.ispan.recordshop.cochordnuts.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_style")
public class ProductStyle {

	@OneToMany(mappedBy = "productStyle", cascade = CascadeType.ALL)
	private Set<Product> products;

//	@ManyToMany(mappedBy = "favoriteMusicType")
//	private List<Member> members = new ArrayList<>(); // 喜好此音樂類型的會員

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer styleNo; // 商品類型編號

	private String styleType; // 商品類型描述

	public ProductStyle() {
	}
	

	@Override
	public String toString() {
		return "ProductStyle [styleNo=" + styleNo + ", styleType=" + styleType + "]";
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

//	public List<Member> getMembers() {
//		return members;
//	}
//
//	public void setMembers(List<Member> members) {
//		this.members = members;
//	}

	public Integer getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(Integer styleNo) {
		this.styleNo = styleNo;
	}

	public String getStyleType() {
		return styleType;
	}

	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	

}
