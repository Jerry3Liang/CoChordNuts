package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import com.ispan.recordshop.cochordnuts.dto.MemberAnswerCaseDto;

import java.util.List;

public interface CustomerCaseDao {

    List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams);

    Integer countCase(CustomerCaseParams customerCaseParams);

    Integer createCase(CustomerCaseRequest customerCaseRequest);

    CustomerCaseRequest getCaseById(Integer customerCaseNo);

    List<MemberAnswerCaseDto> getCaseByMemberNo(Integer memberNo);

    void updateCaseStatus(Integer customerCaseNo, CustomerCaseRequest customerCaseRequest);
}
