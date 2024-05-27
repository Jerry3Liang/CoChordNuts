package com.ispan.recordshop.cochordnuts.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "orderNo"
)
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderNo;// 訂單編號

	@OneToMany(mappedBy = "orderNo", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetail;// 訂單編號

	@ManyToOne // 與Member關聯
	@JsonBackReference
	@JoinColumn(name = "memberNo", referencedColumnName = "memberNo")
	private Member memberNo;// 會員編號

	@OneToOne(mappedBy = "orders")
	private CustomerCase customerCase;

	private String payment;// 付款方式

	private String deliverType;// 運送方式

	private Date createDate;// 下單日

	private String status;// 狀態

	private Date lastModifiedDate;// 最後修改日

	private String receiptNo;// 發票號碼

	private Long totalPay;// 總金額

	private Integer totalCount;// 總數量

	private Integer freight;// 運費

	private String creditCardNo;// 信用卡號

	private String address;// 初始地址

	private String receiptType;// 發票方式

	private String note;// 備註

	private Date preparationDate;// 備貨日

	private Date dispatchDate;// 出貨日

	private Date completeDate;// 完成日

	private String recipient;// 收件人姓名

	private String recipientAddress;// 收件人電話

	private String recipientPhone;// 收件人地址

	public Orders() {
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

//	public Member getMemberNo() {
//		return memberNo;
//	}

	public String getMemberName() {
		return memberNo.getName();
	}
	public String getMemberEmail() {
		return memberNo.getEmail();
	}
	public String getMemberPhone() {
		return memberNo.getPhone();
	}
	public String getMemberAddress() {
		return memberNo.getAddress();
	}
	
	public Integer getMemberNo() {
		return memberNo.getMemberNo();
	}
	public void setMemberNo(Member memberNo) {
		this.memberNo = memberNo;
	}
	
	public CustomerCase getCustomerCase() {
		return customerCase;
	}

	public void setCustomerCase(CustomerCase customerCase) {
		this.customerCase = customerCase;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate() {
		this.createDate = new Date();// 取得現在時間
	}

	public String getStatus() {
		return status;
	}

	public void setStatus() {
		if(preparationDate==null && dispatchDate==null && completeDate==null ) {
			this.status="訂單成立";
		}else if (preparationDate!=null && dispatchDate==null && completeDate==null) {
			this.status="備貨中";
		}else if(preparationDate!=null && dispatchDate!=null && completeDate==null){
			this.status="已出貨";
		}else if(preparationDate!=null && dispatchDate!=null && completeDate!=null){
			this.status="訂單完成";
		}
	
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate() {
		this.lastModifiedDate = new Date();
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo() {
		if(this.createDate!=null) {
			Integer r = 16620962+orderNo;
			String R = "HA-"+ r.toString();
			this.receiptNo =R;
		}else {
			this.receiptNo="";
		}
		
	}

	public Long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Long totalPay) {
		this.totalPay=totalPay;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		
			this.totalCount=totalCount;
		
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight() {
		this.freight = 60;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress() {
			this.address = memberNo.getAddress();
		
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getPreparationDate() {
		return preparationDate;
	}

	public void setPreparationDate(Date preparationDate) {
		this.preparationDate = preparationDate;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
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
