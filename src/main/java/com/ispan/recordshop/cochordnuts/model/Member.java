package com.ispan.recordshop.cochordnuts.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberNo")
	private Integer memberNo; // 會員編號

	@OneToMany(mappedBy = "customerCaseMember")
	private List<CustomerCase> customerCases = new ArrayList<>(); // 所有回覆

	// @OneToMany(mappedBy = "memberNo")
	// private List<Orders> orders = new ArrayList<>(); //所有訂單

	// @ManyToMany
	// @JoinTable(name = "member_favorite_style", joinColumns = @JoinColumn(name =
	// "memberNo"), inverseJoinColumns = @JoinColumn(name = "styleNo"))
	// private List<ProductStyle> favoriteMusicType = new ArrayList<>(); // 喜好音樂類型
	@Column(name = "name")
	private String name; // 會員姓名

	@Column(name = "password", columnDefinition = "nvarchar(MAX)")
	private String password; // 會員密碼

	@Column(name = "email")
	private String email; // 信箱

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	private Date birthday; // 生日

	@Column(name = "address")
	private String address; // 地址

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registerTime")
	private Date registerTime; // 註冊日

	@Column(name = "lastLoginTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime; // 最後登入日

	@Column(name = "phone")
	private String phone; // 電話

	@Column(name = "recipient")
	private String recipient; // 收件人

	@Column(name = "recipientAddress")
	private String recipientAddress; // 收件人地址

	@Column(name = "recipientPhone")
	private String recipientPhone; // 收件人電話

	@Override
	public String toString() {
		return "Member [" + " name=" + name + ", password=" + password + ", email=" + email
				+ ", birthday=" + birthday
				+ ", address=" + address + ", registerTime=" + registerTime + ", lastLoginTime=" + lastLoginTime
				+ ", phone=" + phone + ", recipient=" + recipient + ", recipientAddress=" + recipientAddress
				+ ", recipientPhone=" + recipientPhone + "]";
	}

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
	
	// public List<Orders> getOrders() {
	// return orders;
	// }

	// public void setOrders(List<Orders> orders) {
	// this.orders = orders;
	// }

	// public List<ProductStyle> getFavoriteMusicType() {
	// return favoriteMusicType;
	// }

	// public void setFavoriteMusicType(List<ProductStyle> favoriteMusicType) {
	// this.favoriteMusicType = favoriteMusicType;
	// }

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
