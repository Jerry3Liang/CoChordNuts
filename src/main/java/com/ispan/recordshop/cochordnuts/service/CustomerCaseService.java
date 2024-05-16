package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;

import java.util.List;


public interface CustomerCaseService {

    Integer countCase(CustomerCaseParams customerCaseParams);

    List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams);

    CustomerCaseRequest getCaseById(Integer customerCaseNo);

    void updateCaseStatus(Integer customerCaseNo, CustomerCaseRequest customerCaseRequest);

    boolean deleteCustomerCaseByCaseNo(Integer caseNo);

    boolean existById(Integer id);
}
