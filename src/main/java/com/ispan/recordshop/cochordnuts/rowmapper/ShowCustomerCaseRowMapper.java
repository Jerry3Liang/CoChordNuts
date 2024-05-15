package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowCustomerCaseRowMapper implements RowMapper<CustomerCaseDto> {

    @Override
    public CustomerCaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerCaseDto caseDto = new CustomerCaseDto();
        caseDto.setCaseNo(rs.getInt("case_no"));
        caseDto.setCustomerName(rs.getString("name"));
        caseDto.setSubject(rs.getString("subject"));
        caseDto.setLastAnswerDate(rs.getTimestamp("message_time"));
        caseDto.setAnswerEmployee(rs.getString("emp_name"));
        caseDto.setStatus(rs.getInt("status"));
        return caseDto;
    }
}
