package com.ispan.recordshop.cochordnuts.dao;

import java.util.List;

import com.ispan.recordshop.cochordnuts.model.Role;
import org.json.JSONObject;

import com.ispan.recordshop.cochordnuts.model.Member;

public interface MemberDao {
    List<Member> find(JSONObject obj);

    long count(JSONObject obj);

    // Member login(Member member);


    void addRoleForMemberId(Integer memberId, Role role);
}
