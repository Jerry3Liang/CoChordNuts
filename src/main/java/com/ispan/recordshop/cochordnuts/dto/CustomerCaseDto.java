package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerCaseDto {

    private Integer caseNo;
    private String customerName;
    private String subject;
    private Date lastAnswerDate;
    private String answerEmployee;
    private Integer status;
}
