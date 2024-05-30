package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ShowCustomerCaseRowMapper implements RowMapper<CustomerCaseDto> {

    @Override
    public CustomerCaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerCaseDto caseDto = new CustomerCaseDto();
        caseDto.setCaseNo(rs.getInt("caseNo"));
        caseDto.setCustomerName(rs.getString("name"));
        caseDto.setSubject(rs.getString("subject"));
        String temp = DatetimeConverter.toString(rs.getTimestamp("messageTime"), "yyyy-MM-dd HH:mm:ss");
        caseDto.setLastAnswerDate(temp);
        caseDto.setAnswerEmployee(rs.getString("empName"));
        caseDto.setStatus(rs.getInt("status"));
        return caseDto;
    }
}
