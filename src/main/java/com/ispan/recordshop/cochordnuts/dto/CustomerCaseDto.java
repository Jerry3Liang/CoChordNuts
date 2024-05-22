package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class CustomerCaseDto {

    private Integer caseNo;
    private String customerName;
    private String subject;
    private String lastAnswerDate;
    private String answerEmployee;
    private Integer status;
}
