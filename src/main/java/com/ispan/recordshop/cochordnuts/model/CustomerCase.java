package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_case")
public class CustomerCase implements Serializable {

    //問題回覆編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseNo")
    private Integer caseNo;

    //訂單編號
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderNo", referencedColumnName = "orderNo")
    private Orders orders;

    @OneToMany(mappedBy = "caseNo", cascade = CascadeType.ALL)
    private List<CaseDetail> caseDetails = new ArrayList<>();

    // 客戶編號
    @ManyToOne // 與Member關聯
    @JoinColumn(name = "memberNo", referencedColumnName = "memberNo")
    private Member customerCaseMember;

    //問題主旨
    @Column(name = "subject")
    private String subject;

    //客服問題回覆狀態 => 未回覆： 0   回覆中： 1   回覆完成： 2
    @Column(name = "status")
    private Integer status;

    //無參數 Constructor
    public CustomerCase() {}

    public Integer getCaseNO() {
        return caseNo;
    }

    public void setCaseNO(Integer caseNO) {
        this.caseNo = caseNO;
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

        public Member getMemberNo() {
        return customerCaseMember;
    }

    public void setMemberNo(Member customerCaseMember) {
        this.customerCaseMember = customerCaseMember;
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
