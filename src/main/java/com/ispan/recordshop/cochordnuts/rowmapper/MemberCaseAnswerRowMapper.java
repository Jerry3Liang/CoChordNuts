package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.MemberAnswerCaseDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberCaseAnswerRowMapper implements RowMapper<MemberAnswerCaseDto> {
    @Override
    public MemberAnswerCaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberAnswerCaseDto memberAnswerCaseDto = new MemberAnswerCaseDto();
        memberAnswerCaseDto.setCaseNo(rs.getInt("case_no"));
        memberAnswerCaseDto.setSubject(rs.getString("subject"));
        memberAnswerCaseDto.setMessageTime(rs.getString("message_time"));
        memberAnswerCaseDto.setEmpName(rs.getString("emp_name"));
        memberAnswerCaseDto.setStatus(rs.getInt("status"));

        return memberAnswerCaseDto;
    }
}
