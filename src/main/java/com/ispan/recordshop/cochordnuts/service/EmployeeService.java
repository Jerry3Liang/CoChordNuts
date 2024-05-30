package com.ispan.recordshop.cochordnuts.service;

import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import com.ispan.recordshop.cochordnuts.model.Employee;
import com.ispan.recordshop.cochordnuts.model.Role;

import java.util.List;

public interface EmployeeService {
    EmployeeRequest getEmployeeByName(String name);

    Integer createEmployee(Employee employee);

    List<Role> getRolesByEmployeeId(Integer employeeId);

    void addRoleForEmployeeId(Integer employeeId, Role role);
}
