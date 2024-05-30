package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CaseDetailRowMapper implements RowMapper<CaseDetailRequest> {
    @Override
    public CaseDetailRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        CaseDetailRequest caseRequest = new CaseDetailRequest();
        caseRequest.setCaseDetailNo(rs.getInt("caseDetailNo"));
        caseRequest.setCustomerCaseNo(rs.getInt("caseNo"));
        caseRequest.setAnswerMessage(rs.getString("message"));
        caseRequest.setLastMessageDate(rs.getString("messageTime"));
        caseRequest.setEmployeeNo(rs.getInt("employeeNo"));
        caseRequest.setMemberNo(rs.getInt("member_no"));

        return caseRequest;
    }
}
