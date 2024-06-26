package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.MemberAnswerDto;

import java.util.List;

public interface CaseDetailDao {
    List<CaseDetailDto> getAnswers(CustomerCaseParams customerCaseParams);

    Integer countAnswer(CustomerCaseParams customerCaseParams);

    Integer answerContent(CaseDetailRequest caseDetailRequest);

    void updateContent(Integer caseDetailNo, CaseDetailRequest caseDetailRequest);

    List<CaseDetailDto> findByCaseNo(Integer caseNo);

    CaseDetailRequest getCaseDetailById(Integer caseDetailNo);

    CaseDetailDto findCaseDetailById(Integer caseDetailNo);

    List<MemberAnswerDto> findMemberAnswerByMemberNo(Integer memberNo);

}
