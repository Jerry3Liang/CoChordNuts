package com.ispan.recordshop.cochordnuts.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "roleGroup")
public class RoleGroup {

	@EmbeddedId
	private RoleGroupId roleGroupId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("employeeNo")
	private Employee employeeNo; // 員工編號

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("roleNo")
	private Role roleNo; // 角色編號

	public RoleGroup() {

	}

	public RoleGroupId getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(RoleGroupId roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public Employee getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(Employee employeeNo) {
		this.employeeNo = employeeNo;
	}

	public Role getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(Role roleNo) {
		this.roleNo = roleNo;
	}

}
