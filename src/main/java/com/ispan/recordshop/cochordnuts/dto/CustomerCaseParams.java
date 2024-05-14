package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class CustomerCaseParams {
    private String search;
    private String orderby;
    private String sort;
    private Integer fetch;
    private Integer offset;
}
