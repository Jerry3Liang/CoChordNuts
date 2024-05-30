package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ShowCaseDetailRowMapper implements RowMapper<CaseDetailDto> {

    @Override
    public CaseDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CaseDetailDto caseDetailDto = new CaseDetailDto();
        caseDetailDto.setCaseDetailNo(rs.getInt("caseDetailNo"));
        caseDetailDto.setCaseNo(rs.getInt("caseNo"));
        caseDetailDto.setMessage(rs.getString("message"));
        String temp = DatetimeConverter.toString(rs.getTimestamp("messageTime"), "yyyy-MM-dd HH:mm:ss");
        caseDetailDto.setMessageTime(temp);
        caseDetailDto.setEmployeeName(rs.getString("empName"));
        caseDetailDto.setMemberName(rs.getString("name"));

        return caseDetailDto;
    }
}
