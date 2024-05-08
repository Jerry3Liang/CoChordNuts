package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer_Case")
public class CustomerCase {

    //問題回覆編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseNo")
    private Integer caseNO;

    //訂單編號
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderNo", referencedColumnName = "orderNo")
    private Orders orders;

    @OneToMany(mappedBy = "caseNo", cascade = CascadeType.ALL)
    private List<CaseDetail> caseDetails = new ArrayList<>();

    //客戶編號
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "memberNo")
    private Member member;



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

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<CaseDetail> getCaseDetails() {
        return caseDetails;
    }

    public void setCaseDetails(List<CaseDetail> caseDetails) {
        this.caseDetails = caseDetails;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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