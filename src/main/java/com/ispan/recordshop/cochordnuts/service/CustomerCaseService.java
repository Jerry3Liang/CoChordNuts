package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import com.ispan.recordshop.cochordnuts.dto.MemberAnswerCaseDto;
import com.ispan.recordshop.cochordnuts.model.CustomerCase;

import java.util.List;


public interface CustomerCaseService {

    Integer countCase(CustomerCaseParams customerCaseParams);

    List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams);

    Integer createCase(CustomerCaseRequest customerCaseRequest);

    CustomerCaseRequest getCaseById(Integer customerCaseNo);

    CustomerCase findById(Integer caseNo);

    List<MemberAnswerCaseDto> getCaseByMemberNo(Integer memberNo);

    void updateCaseStatus(Integer customerCaseNo, CustomerCaseRequest customerCaseRequest);

    boolean deleteCustomerCaseByCaseNo(Integer caseNo);

    boolean existById(Integer id);
}
