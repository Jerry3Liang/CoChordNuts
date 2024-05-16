package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CaseDetail;

import java.util.List;

public interface CaseDetailService {

    List<CaseDetailDto> getAnswers(CustomerCaseParams customerCaseParams);

    Integer countAnswer(CustomerCaseParams customerCaseParams);

    Integer answerContent(CaseDetailRequest caseRequest);

    CaseDetail updateContent(CaseDetailDto updateContent);


    CaseDetail findById(Integer id);

    List<CaseDetailDto> findByCaseNo(Integer caseNo);

    boolean existById(Integer id);
}
