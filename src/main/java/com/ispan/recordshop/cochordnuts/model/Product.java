package com.ispan.recordshop.cochordnuts.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "product")
public class Product {

	@ManyToOne
	@JoinColumn(
			name = "styleNo",
			referencedColumnName = "styleNo")
	private ProductStyle productStyle;
	
	@ManyToOne
	@JoinColumn(
			name = "artistNo",
			referencedColumnName = "artistNo")
	private Artist artist;
	
	@ManyToOne
	@JoinColumn(
			name = "languageNo",
			referencedColumnName = "languageNo")
	private Language language;
	
	@ManyToOne
	@JoinColumn(
			name = "musicYearNo",
			referencedColumnName = "musicYearNo")
	private MusicYear musicYear;
	
	@OneToMany(
			mappedBy="productNo",
			cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetail;
	
	@OneToMany(
			mappedBy="product",
			cascade = CascadeType.ALL)
	private Set<WishList> wishList;
	
	@OneToMany(
			mappedBy="product",
			cascade = CascadeType.ALL)
	private Set<Cart> Cart;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productNo; //產品編號
	
	private String productName; //產品名稱
	
	private Integer stock; //庫存
	
	private Integer unitPrice; //單價(原價)
	
	@Lob
	@Column(columnDefinition = "nvarchar(MAX)")
	private String describe; //商品描述
	
	@Column(columnDefinition = "varbinary(MAX)")
	private byte[] photo; //圖片
	
//	private Integer styleNo; //類型
	
//	private Integer artistNo; //藝人
	
//	private Integer languageNo; //語言
	
	private Date publishedDate; //發行日
	
	private Date lastModifiedDate; //最後修改日
	
	private Integer isDiscount; //是否為折扣商品

	private Integer isBest; //是否為暢銷商品

	private Integer isPreorder; //是否為預購商品

	private Integer productStatus; //商品狀態
	
	private double discount; //折扣
	
//	private Integer musicYearNo; //音樂年份
	
	public Product() {
	}
	
	
	
	@Override
	public String toString() {
		return "Product [productStyle=" + productStyle + ", artist=" + artist + ", language=" + language
				+ ", musicYear=" + musicYear + ", productNo=" + productNo + ", productName=" + productName + ", stock="
				+ stock + ", unitPrice=" + unitPrice + ", describe=" + describe + ", publishedDate=" + publishedDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", isDiscount=" + isDiscount + ", isBest=" + isBest
				+ ", isPreorder=" + isPreorder + ", productStatus=" + productStatus + ", discount=" + discount + "]";
	}

//	@Override //有photo版本
//	public String toString() {
//		return "Product [productStyle=" + productStyle + ", artist=" + artist + ", language=" + language
//				+ ", musicYear=" + musicYear + ", productNo=" + productNo + ", productName=" + productName + ", stock="
//				+ stock + ", unitPrice=" + unitPrice + ", describe=" + describe + ", photo=" + Arrays.toString(photo)
//				+ ", publishedDate=" + publishedDate + ", lastModifiedDate=" + lastModifiedDate + ", isDiscount="
//				+ isDiscount + ", isBest=" + isBest + ", isPreorder=" + isPreorder + ", productStatus=" + productStatus
//				+ ", discount=" + discount + "]";
//	}






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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	

//	public Integer getStyleNo() {
//		return styleNo;
//	}
//
//	public void setStyleNo(Integer styleNo) {
//		this.styleNo = styleNo;
//	}

//	public Integer getArtistNo() {
//		return artistNo;
//	}
//
//	public void setArtistNo(Integer artistNo) {
//		this.artistNo = artistNo;
//	}

//	public Integer getLanguageNo() {
//		return languageNo;
//	}
//
//	public void setLanguageNo(Integer languageNo) {
//		this.languageNo = languageNo;
//	}

	
	public byte[] getPhoto() {
		return photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
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

//	public Integer getMusicYearNo() {
//		return musicYearNo;
//	}
//
//	public void setMusicYearNo(Integer musicYearNo) {
//		this.musicYearNo = musicYearNo;
//	}

	public ProductStyle getProductStyle() {
		return productStyle;
	}

	public void setProductStyle(ProductStyle productStyle) {
		this.productStyle = productStyle;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public MusicYear getMusicYear() {
		return musicYear;
	}

	public void setMusicYear(MusicYear musicYear) {
		this.musicYear = musicYear;
	}

	public Set<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(Set<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Set<WishList> getWishList() {
		return wishList;
	}

	public void setWishList(Set<WishList> wishList) {
		this.wishList = wishList;
	}


	public Set<Cart> getCart() {
		return Cart;
	}


	public void setCart(Set<Cart> cart) {
		Cart = cart;
	}

	
	
	
	
	
	

}
