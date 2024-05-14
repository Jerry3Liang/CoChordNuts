package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CaseDetailRowMapper implements RowMapper<CaseDetailDto> {

    @Override
    public CaseDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CaseDetailDto caseDetailDto = new CaseDetailDto();
        caseDetailDto.setCaseDetailNo(rs.getInt("case_detail_no"));
        caseDetailDto.setCaseNo(rs.getInt("case_no"));
        caseDetailDto.setMessage(rs.getString("message"));
        String temp = DatetimeConverter.toString(rs.getTimestamp("message_time"), "yyyy-MM-dd HH:mm:ss");
        caseDetailDto.setMessageTime(temp);
        caseDetailDto.setEmployeeName(rs.getString("name"));

        return caseDetailDto;
    }
}
