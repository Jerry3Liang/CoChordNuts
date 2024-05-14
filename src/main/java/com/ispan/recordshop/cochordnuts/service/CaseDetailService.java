package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CaseDetail;

import java.util.List;

public interface CaseDetailService {

    List<CaseDetailDto> getAnswers(CustomerCaseParams customerCaseParams);

    Integer countAnswer(CustomerCaseParams customerCaseParams);

    CaseDetail answerContent(CaseDetailDto content);

    CaseDetail updateContent(CaseDetailDto updateContent);


    boolean existById(Integer id);
}
