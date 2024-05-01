package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Case_Detail")
public class CaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseDetailNo")
    private Integer caseDetailNo;

    @Column(name = "caseNo")
    private Integer caseNo;

    @Column(name = "message")
    private String message;

    @Column(name = "messageTime")
    private Date messageTime;

    @Column(name = "employeeNo")
    private Integer employeeNo;

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
