package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeNo; //員工編號

	@OneToMany(mappedBy = "employeeNo", cascade = CascadeType.ALL)
	private List<CaseDetail> caseDetails = new ArrayList<>();
	
	private String name; //員工姓名
	
	private String password; //員工密碼

	public Employee() {
		
	}

	public Integer getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
