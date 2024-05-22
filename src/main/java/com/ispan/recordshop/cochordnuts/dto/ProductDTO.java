package com.ispan.recordshop.cochordnuts.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Integer productNo; //產品編號
	private String productName; //產品名稱
	private Integer unitPrice; //單價(原價)
	private Integer stock; //庫存
	private String describe; //商品描述
	private String publishedDate; //發行日
	private double discount; //折扣
	private byte[] photo; //圖片
	private String styleType; //類型
	private String artistType; //藝人
	private String languageType; //語言
	private String musicYear; //音樂年份
	private Integer productStatus; //商品狀態
	private Integer isDiscount; //是否為折扣商品
	private Integer isBest; //是否為暢銷商品
	private Integer isPreorder; //是否為預購商品
	
	public ProductDTO() {
	}


	
	
	

}
