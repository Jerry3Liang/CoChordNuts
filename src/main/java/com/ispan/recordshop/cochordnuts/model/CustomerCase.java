package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer_Case")
public class CustomerCase implements Serializable {

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

    //問題主旨
    @Column(name = "subject")
    private String subject;

    //客服問題回覆狀態 => 未回覆： 0   回覆中： 1   回覆完成： 2
    @Column(name = "status")
    private Integer status;

    @Column(name = "memberName")
    private String memberName;

    //無參數 Constructor
    public CustomerCase() {}

//    @JsonCreator
//    public CustomerCase(@JsonProperty("caseNo") Integer caseNO,
//                        Orders orders,
//                        List<CaseDetail> caseDetails,
//                        String subject,
//                        Integer status,
//                        String memberName) {
//        this.caseNO = caseNO;
//        this.orders = orders;
//        this.caseDetails = caseDetails;
//        this.subject = subject;
//        this.status = status;
//        this.memberName = memberName;
//    }

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
