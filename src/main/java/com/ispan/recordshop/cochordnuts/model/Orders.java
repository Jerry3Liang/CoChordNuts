package com.ispan.recordshop.cochordnuts.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderNo;//訂單編號
		
	private Integer memberNo;//會員編號
	
	private Date createDate;//下單日
	
	private Date preparationDate;//備貨日
	
	private Date dispatchDate;//出貨日
	
	private Date completeDate;//完成日
	
	private Date cnacelDate;//取消日
	
	private Date returnDate;//退貨日
	
	private String deliverType;//運送方式
	
	private Integer freight;//運費
	
	private Integer totalCount;//總數量
	
	private Integer totalPay;//總金額
	
	private String payment;//付款方式
	
	private String creditCardNo;//信用卡號
	
	private String receiptType;//發票方式
		
	private String receiptNo;//發票號碼

	private String recipient;//收件人姓名
	
    private String recipientAddress;//收件人電話
	
	private String recipientPhone;//收件人地址
		
	private String note;//備註
	
	public Orders() {
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getPreparationDate() {
		return preparationDate;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public Date getCnacelDate() {
		return cnacelDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public Integer getFreight() {
		return freight;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getTotalPay() {
		return totalPay;
	}

	public String getPayment() {
		return payment;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public String getRecipientPhone() {
		return recipientPhone;
	}

	public String getNote() {
		return note;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setPreparationDate(Date preparationDate) {
		this.preparationDate = preparationDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public void setCnacelDate(Date cnacelDate) {
		this.cnacelDate = cnacelDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalPay(Integer totalPay) {
		this.totalPay = totalPay;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
