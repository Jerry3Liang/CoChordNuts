package com.ispan.recordshop.cochordnuts.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberNo; //會員編號
	
	private String name; //會員姓名
	
	private String password; //會員密碼
	
	private String email; //信箱
	
	private Date birthday; //生日
	
	private String address; //地址
	
	private Date registerTime; //註冊日
	
	private Date lastLoginTime; //最後登入日
	
	private String favoriteMusicType; //喜好音樂類型
	
	private String phone; //電話
	
	private String recipient; //收件人
	
	private String recipientAddress; //收件人地址
	
	private String recipientPhone; //收件人電話

	

	public Employee() {
		
	}


	public Integer getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getRegisterTime() {
		return registerTime;
	}


	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}


	public Date getLastLoginTime() {
		return lastLoginTime;
	}


	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public String getFavoriteMusicType() {
		return favoriteMusicType;
	}


	public void setFavoriteMusicType(String favoriteMusicType) {
		this.favoriteMusicType = favoriteMusicType;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getRecipient() {
		return recipient;
	}


	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	public String getRecipientAddress() {
		return recipientAddress;
	}


	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}


	public String getRecipientPhone() {
		return recipientPhone;
	}


	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}
	
	

}
