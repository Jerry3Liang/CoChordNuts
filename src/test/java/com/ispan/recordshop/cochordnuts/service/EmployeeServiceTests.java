package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import com.ispan.recordshop.cochordnuts.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getEmployeeByName(){
        EmployeeRequest emp = employeeService.getEmployeeByName("JerryLiang");
        System.out.println(emp.toString());
    }

    @Test
    public void login(){
        Employee employee = employeeService.login("Jason", "12345");

        System.out.println(employee.getName());
    }
}
