package com.ispan.recordshop.cochordnuts.security;

import com.ispan.recordshop.cochordnuts.dto.EmployeeRequest;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //從 DB 中查詢 Employee 資料
        EmployeeRequest emp = employeeService.getEmployeeByName(username);

        if(emp == null){
            throw new UsernameNotFoundException("找不到員工名稱為： " + username + " 的員工");
        } else {
            String empName = emp.getName();
            String empPassword = emp.getPassword();

            //權限部分
            List<Role> roleList = employeeService.getRolesByEmployeeId(emp.getEmployeeNo());
            List<GrantedAuthority> authorities = convertToAuthorities(roleList);

            //轉換成 Spring Security 指定的 User 格式
            return new User(empName, empPassword, authorities);
        }
    }

    private List<GrantedAuthority> convertToAuthorities(List<Role> roleList) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roleList){
            authorities.add(new SimpleGrantedAuthority(role.getRoleType()));
        }

        return authorities;
    }
}
