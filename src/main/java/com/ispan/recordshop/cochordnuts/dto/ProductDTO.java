package com.ispan.recordshop.cochordnuts.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Integer productNo; //產品編號
	private String productName; //產品名稱
	private Integer unitPrice; //單價(原價)
	private String describe; //商品描述
	private Date publishedDate; //發行日
	private double discount; //折扣
	private byte[] photo; //圖片
	private String styleType; //類型
	private String artistType; //藝人
	private String languageType; //語言
	private String musicYear; //音樂年份
	
	
	public ProductDTO() {
	}


	public Integer getProductNo() {
		return productNo;
	}


	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Integer getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getDescribe() {
		return describe;
	}


	public void setDescribe(String describe) {
		this.describe = describe;
	}


	public Date getPublishedDate() {
		return publishedDate;
	}


	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	public String getStyleType() {
		return styleType;
	}


	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}


	public String getArtistType() {
		return artistType;
	}


	public void setArtistType(String artistType) {
		this.artistType = artistType;
	}


	public String getLanguageType() {
		return languageType;
	}


	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}


	public String getMusicYear() {
		return musicYear;
	}


	public void setMusicYear(String musicYear) {
		this.musicYear = musicYear;
	}
	
	
	
	

}
