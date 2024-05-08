package com.ispan.recordshop.cochordnuts.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_style")
public class ProductStyle {
	
	@OneToMany(
			mappedBy = "productStyle",
			cascade = CascadeType.ALL)
	private Set<Product> products;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer styleNo; //商品類型編號
	
	private String styleType; //商品類型描述

	public ProductStyle() {
	}

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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	

}
