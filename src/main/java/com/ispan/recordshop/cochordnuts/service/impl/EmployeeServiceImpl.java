package com.ispan.recordshop.cochordnuts.service.impl;

import com.ispan.recordshop.cochordnuts.dao.EmployeeDao;
import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import com.ispan.recordshop.cochordnuts.model.Employee;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.repository.EmployeeRepository;
import com.ispan.recordshop.cochordnuts.service.EmployeeService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public EmployeeRequest getEmployeeByName(String name) {
        return employeeDao.getEmpByName(name);
    }

    @Override
    public Integer createEmployee(Employee employee) {
        return employeeDao.createEmployee(employee);
    }

    @Override
    public Employee login(String name, String password) {
        if (name != null && !name.isEmpty() && password != null && !password.isEmpty()) {
            Optional<Employee> optional = employeeRepository.findByName(name);
            if (optional.isPresent()) {
                Employee employee = optional.get();
                String storedPasswordHash = employee.getPassword();

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, storedPasswordHash)) {
                    return employee;
                }
            }
        }

        return null;
    }

    @Override
    public List<Role> getRolesByEmployeeId(Integer employeeId) {
        return employeeDao.getRolesByEmployeeId(employeeId);
    }

    @Override
    public void addRoleForEmployeeId(Integer employeeId, Role role) {
        employeeDao.addRoleForEmployeeId(employeeId, role);
    }

    public Employee logout(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer employeeNo = obj.isNull("employeeNo") ? null : obj.getInt("employeeNo");

            if (employeeNo != null) {
                Optional<Employee> optional = employeeRepository.findById(employeeNo);
                if (optional.isPresent()) {
                    Employee update = optional.get();
                    String lastLoginTime = obj.isNull("lastLoginTime") ? null : obj.getString("lastLoginTime");
                    java.util.Date loginTime = DatetimeConverter.parse(lastLoginTime, "yyyy-MM-dd HH:mm:ss");
                    update.setLastLoginTime(loginTime);
                    return employeeRepository.save(update);
                } else {
                    return null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
