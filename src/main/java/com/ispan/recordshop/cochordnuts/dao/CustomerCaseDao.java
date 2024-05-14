package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;

import java.util.List;

public interface CustomerCaseDao {

    List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams);

    Integer countCase(CustomerCaseParams customerCaseParams);
}
