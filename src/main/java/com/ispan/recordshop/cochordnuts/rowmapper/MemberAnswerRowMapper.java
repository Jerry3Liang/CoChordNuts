package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.MemberAnswerDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberAnswerRowMapper implements RowMapper<MemberAnswerDto> {
    @Override
    public MemberAnswerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberAnswerDto memberAnswerDto = new MemberAnswerDto();
        memberAnswerDto.setAnswerMessage(rs.getString("message"));
        memberAnswerDto.setMessageTime(rs.getString("messageTime"));
        memberAnswerDto.setMemberName(rs.getString("name"));

        return memberAnswerDto;
    }
}
