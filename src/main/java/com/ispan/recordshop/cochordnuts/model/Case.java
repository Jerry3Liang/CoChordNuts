package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Case")
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseNo")
    private Integer caseNO;

    @Column(name = "memberNo")
    private Integer memberNo;

    @Column(name = "orderNo")
    private Integer orderNo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "status")
    private Integer status;

    public Case() {}

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
