package com.ispan.recordshop.cochordnuts.dto;

import lombok.Data;

@Data
public class EmployeeRequest {
    private Integer employeeNo;
    private String name;
    private String password;
}
