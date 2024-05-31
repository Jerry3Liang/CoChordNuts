package com.ispan.recordshop.cochordnuts.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerCaseTests {

    @Autowired
    private CustomerCaseService customerCaseService;

    @Test
    public void caseExitByOderId(){
        boolean caseExit = customerCaseService.caseExitByOrderNo(3);
        System.out.println(caseExit);
    }
}
