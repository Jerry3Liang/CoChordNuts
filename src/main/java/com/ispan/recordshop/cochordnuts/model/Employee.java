package com.ispan.recordshop.cochordnuts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeNo; //員工編號

	@OneToMany(mappedBy = "employeeNo", cascade = CascadeType.ALL)
	private List<CaseDetail> caseDetails = new ArrayList<>();

	@Column(name = "empName")
	private String name; //員工姓名
	
	private String password; //員工密碼

	public Employee() {
		
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lastLoginTime")
	private Date lastLoginTime; // 最後登入日

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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"employeeNo=" + employeeNo +
				", caseDetails=" + caseDetails +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", lastLoginTime=" + lastLoginTime +
				'}';
	}
}
