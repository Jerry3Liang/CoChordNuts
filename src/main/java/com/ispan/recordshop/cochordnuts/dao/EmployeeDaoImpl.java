package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import com.ispan.recordshop.cochordnuts.model.Employee;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.rowmapper.EmployeeRowMapper;
import com.ispan.recordshop.cochordnuts.rowmapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeDaoImpl implements EmployeeDao{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public EmployeeRequest getEmpByName(String name) {
        String sql = "SELECT employeeNo, empName, password FROM employee WHERE empName = :name";

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);

        List<EmployeeRequest> employeeList = namedParameterJdbcTemplate.query(sql, map, new EmployeeRowMapper());

        if(employeeList.size() > 0){
            return employeeList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createEmployee(Employee employee) {
        String sql = "INSERT INTO employee (empName, password) VALUES (:name, :password)";

        Map<String, Object> map = new HashMap<>();
        map.put("name", employee.getName());
        map.put("password", employee.getPassword());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int employeeId = keyHolder.getKey().intValue();

        return employeeId;
    }

    @Override
    public List<Role> getRolesByEmployeeId(Integer employeeId) {
        String sql = "SELECT r.roleNo, r.roleType FROM role r " +
                     "JOIN roleGroup rg ON r.roleNo = rg.roleNo_roleNo " +
                     "WHERE rg.employeeNo_employeeNo = :employeeId";

        Map<String, Object> map = new HashMap<>();
        map.put("employeeId", employeeId);

        List<Role> roleList = namedParameterJdbcTemplate.query(sql, map, new RoleRowMapper());

        return roleList;
    }

    @Override
    public void addRoleForEmployeeId(Integer employeeId, Role role) {
        String sql = "INSERT INTO roleGroup (employeeNo_employeeNo, roleNo_roleNo) VALUES (:employeeId, :roleId)";

        Map<String, Object> map = new HashMap<>();
        map.put("employeeId", employeeId);
        map.put("roleId", role.getRoleNo());

        namedParameterJdbcTemplate.update(sql, map);
    }
}
