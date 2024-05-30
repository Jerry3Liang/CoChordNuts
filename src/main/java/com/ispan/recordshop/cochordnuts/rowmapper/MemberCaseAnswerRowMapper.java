package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.MemberAnswerCaseDto;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberCaseAnswerRowMapper implements RowMapper<MemberAnswerCaseDto> {
    @Override
    public MemberAnswerCaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberAnswerCaseDto memberAnswerCaseDto = new MemberAnswerCaseDto();
        memberAnswerCaseDto.setCaseNo(rs.getInt("caseNo"));
        memberAnswerCaseDto.setSubject(rs.getString("subject"));
        String temp = DatetimeConverter.toString(rs.getTimestamp("messageTime"), "yyyy-MM-dd HH:mm:ss");
        memberAnswerCaseDto.setMessageTime(temp);
        memberAnswerCaseDto.setEmpName(rs.getString("empName"));
        memberAnswerCaseDto.setStatus(rs.getInt("status"));

        return memberAnswerCaseDto;
    }
}
