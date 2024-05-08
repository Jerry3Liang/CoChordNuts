package com.ispan.recordshop.cochordnuts.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberNo; // 會員編號

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<CustomerCase> customerCases = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Orders> orders = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "member_favorite_style", joinColumns = @JoinColumn(name = "memberNo"), inverseJoinColumns = @JoinColumn(name = "styleNo"))
	private List<ProductStyle> favoriteMusicType = new ArrayList<>(); // 喜好音樂類型

	private String name; // 會員姓名

	private String password; // 會員密碼

	private String email; // 信箱

	private Date birthday; // 生日

	private String address; // 地址

	private Date registerTime; // 註冊日

	private Date lastLoginTime; // 最後登入日

	private String phone; // 電話

	private String recipient; // 收件人

	private String recipientAddress; // 收件人地址

	private String recipientPhone; // 收件人電話

	public Member() {
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public List<CustomerCase> getCustomerCases() {
		return customerCases;
	}

	public void setCustomerCases(List<CustomerCase> customerCases) {
		this.customerCases = customerCases;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<ProductStyle> getFavoriteMusicType() {
		return favoriteMusicType;
	}

	public void setFavoriteMusicType(List<ProductStyle> favoriteMusicType) {
		this.favoriteMusicType = favoriteMusicType;
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
