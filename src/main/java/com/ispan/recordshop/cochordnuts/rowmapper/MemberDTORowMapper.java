package com.ispan.recordshop.cochordnuts.rowmapper;

import com.ispan.recordshop.cochordnuts.dto.MemberDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDTORowMapper implements RowMapper<MemberDTO> {

    @Override
    public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setMemberNo(rs.getInt("memberNo"));
                memberDTO.setRegisterTime(rs.getString("registerTime"));
                memberDTO.setLastLoginTime(rs.getString("lastLoginTime"));
                memberDTO.setRecipient(rs.getString("recipient"));
                memberDTO.setRecipientAddress(rs.getString("recipientAddress"));
                memberDTO.setRecipientPhone(rs.getString("recipientPhone"));
                memberDTO.setName(rs.getString("name"));
                memberDTO.setBirthday(rs.getString("birthday"));
                memberDTO.setPassword(rs.getString("password"));
                memberDTO.setPhone(rs.getString("phone"));
                memberDTO.setEmail(rs.getString("email"));
                memberDTO.setAddress(rs.getString("address"));
                memberDTO.setMemberStatus(rs.getInt("memberStatus"));

        return memberDTO;
    }
}
