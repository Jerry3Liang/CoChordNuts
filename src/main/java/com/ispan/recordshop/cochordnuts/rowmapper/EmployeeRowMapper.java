package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapper implements RowMapper<EmployeeRequest> {

    @Override
    public EmployeeRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployeeNo(rs.getInt("employeeNo"));
        employeeRequest.setName(rs.getString("empName"));
        employeeRequest.setPassword(rs.getString("password"));

        return employeeRequest;
    }
}
