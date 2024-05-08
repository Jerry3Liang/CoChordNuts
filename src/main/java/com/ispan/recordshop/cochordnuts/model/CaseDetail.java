package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Case_Detail")
public class CaseDetail {

    //回覆問題詳細內容編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseDetailNo")
    private Integer caseDetailNo;

    //問題回覆編號
    @Column(name = "caseNo")
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "caseNo")
    private Integer caseNo;

    //回覆內容
    @Column(name = "message")
    private String message;

    //回覆時間
    @Column(name = "messageTime")
    private Date messageTime;

    //回覆員工編號
    @Column(name = "employeeNo")
    private Integer employeeNo;

    //無參數 Constructor
    public CaseDetail(){}

    public Integer getCaseDetailNo() {
        return caseDetailNo;
    }

    public void setCaseDetailNo(Integer caseDetailNo) {
        this.caseDetailNo = caseDetailNo;
    }

    public Integer getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(Integer caseNo) {
        this.caseNo = caseNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public Integer getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }
}
