package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.rowmapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleDaoImpl implements RoleDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Role getRoleByName(String roleName) {
        String sql = "SELECT roleNo, roleType FROM role WHERE roleType = :roleName";
        Map<String, Object> map = new HashMap<>();
        map.put("roleName", roleName);

        List<Role> roleList = namedParameterJdbcTemplate.query(sql, map, new RoleRowMapper());

        if(roleList.isEmpty()){
            return null;
        } else {
            return roleList.get(0);
        }
    }
}
