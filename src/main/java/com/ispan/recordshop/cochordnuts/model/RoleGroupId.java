package com.ispan.recordshop.cochordnuts.model;

import java.util.Objects;
import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class RoleGroupId implements Serializable {

    private Integer employeeNo; // 員工編號

    private Integer roleNo; // 角色編號

    public RoleGroupId() {

    }

    public Integer getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Integer getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(Integer roleNo) {
        this.roleNo = roleNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNo, roleNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RoleGroupId other = (RoleGroupId) obj;
        return Objects.equals(employeeNo, other.employeeNo) && Objects.equals(roleNo, other.roleNo);
    }

}
