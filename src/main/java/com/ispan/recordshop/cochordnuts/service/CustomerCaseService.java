package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;

import java.util.List;


public interface CustomerCaseService {

    Integer countCase(CustomerCaseParams customerCaseParams);

    List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams);

    boolean deleteCustomerCaseByCaseNo(Integer caseNo);

    boolean existById(Integer id);
}
