package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class MemberAnswerCaseDto {
    private Integer caseNo;
    private String subject;
    private String messageTime;
    private String empName;
    private Integer status;
}
