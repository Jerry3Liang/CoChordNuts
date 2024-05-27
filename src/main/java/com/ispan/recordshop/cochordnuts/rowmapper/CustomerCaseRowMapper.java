package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerCaseRowMapper implements RowMapper<CustomerCaseRequest> {

    @Override
    public CustomerCaseRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerCaseRequest customerCase = new CustomerCaseRequest();
        customerCase.setCustomerCaseNo(rs.getInt("case_no"));
        customerCase.setSubject(rs.getString("subject"));
        customerCase.setStatus(rs.getInt("status"));
        customerCase.setOrderNo(rs.getInt("order_no"));
        customerCase.setMemberNo(rs.getInt("member_no"));
        return customerCase;
    }
}
