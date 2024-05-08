package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Customer_Case")
public class CustomerCase {

    //問題回覆編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseNo")
    private Integer caseNO;

    //客戶編號
    @Column(name = "memberNo")
    private Integer memberNo;

    //訂單編號
    @Column(name = "orderNo")
    private Integer orderNo;

    //問題主旨
    @Column(name = "subject")
    private String subject;

    //客服問題回覆狀態 => 未回覆： 0   回覆中： 1   回覆完成： 2
    @Column(name = "status")
    private Integer status;

    //無參數 Constructor
    public CustomerCase() {}

    public Integer getCaseNO() {
        return caseNO;
    }

    public void setCaseNO(Integer caseNO) {
        this.caseNO = caseNO;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
