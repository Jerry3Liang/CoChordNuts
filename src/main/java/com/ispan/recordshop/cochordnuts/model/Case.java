package com.ispan.recordshop.cochordnuts.model;

public class Case {
    private Integer caseNO;

    private Integer memberNo;

    private Integer orderNo;

    private String subject;

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
