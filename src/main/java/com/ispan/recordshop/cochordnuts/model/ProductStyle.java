package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productStyle")
public class ProductStyle {
	
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
	
	

}
