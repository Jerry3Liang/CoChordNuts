package com.ispan.recordshop.cochordnuts.repository;

import com.ispan.recordshop.cochordnuts.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
