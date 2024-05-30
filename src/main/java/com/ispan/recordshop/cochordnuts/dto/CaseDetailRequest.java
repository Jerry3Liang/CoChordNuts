package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class CaseDetailRequest {
    private Integer caseDetailNo;
    private Integer customerCaseNo;
    private String answerMessage;
    private String lastMessageDate;
    private Integer employeeNo;
    private Integer memberNo;
}
