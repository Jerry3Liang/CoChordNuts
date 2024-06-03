package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import com.ispan.recordshop.cochordnuts.dto.MemberAnswerCaseDto;

import java.util.List;

public interface CustomerCaseDao {

    List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams);

    List<CustomerCaseDto> getCaseByEmployeeNo(CustomerCaseParams customerCaseParams, Integer employeeNo);

    Integer countCase(CustomerCaseParams customerCaseParams);

    Integer createCase(CustomerCaseRequest customerCaseRequest);

    CustomerCaseRequest getCaseById(Integer customerCaseNo);

    List<MemberAnswerCaseDto> getCaseByMemberNo(Integer memberNo);

    boolean caseExitByOrderNo(Integer orderNo);

    void updateCaseStatus(Integer customerCaseNo, CustomerCaseRequest customerCaseRequest);
}
