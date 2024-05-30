package com.ispan.recordshop.cochordnuts.service.impl;

import com.ispan.recordshop.cochordnuts.dao.EmployeeDao;
import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import com.ispan.recordshop.cochordnuts.model.Employee;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.repository.EmployeeRepository;
import com.ispan.recordshop.cochordnuts.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<Role> getRolesByEmployeeId(Integer employeeId) {
        return employeeDao.getRolesByEmployeeId(employeeId);
    }

    @Override
    public void addRoleForEmployeeId(Integer employeeId, Role role) {
        employeeDao.addRoleForEmployeeId(employeeId, role);
    }
}
