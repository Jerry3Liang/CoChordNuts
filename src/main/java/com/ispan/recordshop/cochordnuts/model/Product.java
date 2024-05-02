package com.ispan.recordshop.cochordnuts.model;

import java.sql.Clob;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productNo; //產品編號
	
	private String productName; //產品名稱
	
	private Integer stock; //庫存
	
	private Integer unitPrice; //單價(原價)
	
	private Clob describe; //商品描述
	
	private String photoPath; //圖片路徑
	
	private Integer styleNo; //類型
	
	private Integer artistNo; //藝人
	
	private Integer language; //語言
	
	private Date publishedDate; //發行日
	
	private Date lastModifiedDate; //最後修改日
	
	private Integer isDiscount; //是否為折扣商品

	private Integer isBest; //是否為暢銷商品

	private Integer isPreorder; //是否為預購商品

	private Integer productStatus; //商品狀態
	
	private double discount; //折扣
	
	private Integer musicYearNo; //音樂年份
	
	public Product() {
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Clob getDescribe() {
		return describe;
	}

	public void setDescribe(Clob describe) {
		this.describe = describe;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Integer getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(Integer styleNo) {
		this.styleNo = styleNo;
	}

	public Integer getArtistNo() {
		return artistNo;
	}

	public void setArtistNo(Integer artistNo) {
		this.artistNo = artistNo;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}

	public Integer getIsBest() {
		return isBest;
	}

	public void setIsBest(Integer isBest) {
		this.isBest = isBest;
	}

	public Integer getIsPreorder() {
		return isPreorder;
	}

	public void setIsPreorder(Integer isPreorder) {
		this.isPreorder = isPreorder;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Integer getMusicYearNo() {
		return musicYearNo;
	}

	public void setMusicYearNo(Integer musicYearNo) {
		this.musicYearNo = musicYearNo;
	}
	
	

}
