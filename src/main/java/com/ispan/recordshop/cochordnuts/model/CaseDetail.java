package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Case_Detail")
public class CaseDetail implements Serializable {



    //回覆問題詳細內容編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseDetailNo")
    private Integer caseDetailNo;

    //問題回覆編號
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caseNo")
    private CustomerCase caseNo;

    //回覆內容
    @Column(name = "message")
    private String message;

    //回覆時間
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "messageTime")
    private Date messageTime;

    //回覆員工編號
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeNo")
    private Employee employeeNo;

    //無參數 Constructor
    public CaseDetail(){}

//    @JsonCreator
//    public CaseDetail(Integer caseDetailNo, @JsonProperty("caseNo") CustomerCase caseNo, @JsonProperty("message") String message, Date messageTime, Employee employeeNo) {
//        this.caseDetailNo = caseDetailNo;
//        this.caseNo = caseNo;
//        this.message = message;
//        this.messageTime = messageTime;
//        this.employeeNo = employeeNo;
//    }

    public Integer getCaseDetailNo() {
        return caseDetailNo;
    }

    public void setCaseDetailNo(Integer caseDetailNo) {
        this.caseDetailNo = caseDetailNo;
    }

    public CustomerCase getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(CustomerCase caseNo) {
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

    public Employee getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Employee employeeNo) {
        this.employeeNo = employeeNo;
    }
}
