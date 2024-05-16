package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CaseDetailRowMapper implements RowMapper<CaseDetailRequest> {
    @Override
    public CaseDetailRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        CaseDetailRequest caseRequest = new CaseDetailRequest();
        caseRequest.setCaseDetailNo(rs.getInt("case_detail_no"));
        caseRequest.setCustomerCaseNo(rs.getInt("case_no"));
        caseRequest.setAnswerMessage(rs.getString("message"));
        caseRequest.setLastMessageDate(rs.getString("message_time"));
        caseRequest.setEmployeeNo(rs.getInt("employee_no"));

        return caseRequest;
    }
}
