package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class CustomerCaseRequest {
    private Integer customerCaseNo;
    private String subject;
    private Integer status;
    private Integer orderNo;
    private Integer memberNo;
}
