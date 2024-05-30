package com.ispan.recordshop.cochordnuts.controller;

import cn.hutool.core.date.DateUtil;
import com.ispan.recordshop.cochordnuts.dto.PayLoadDto;
import com.ispan.recordshop.cochordnuts.model.Employee;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.service.EmployeeService;
import com.ispan.recordshop.cochordnuts.service.RoleService;
import com.ispan.recordshop.cochordnuts.util.JWTUtil;
import com.ispan.recordshop.cochordnuts.util.JsonWebTokenUtility;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/rest")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;

    @PostMapping("/register")
    public String register(@RequestBody Employee employee){
        //hash 原始密碼
        String hashedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(hashedPassword);

        //在 DB 新增 Employee 資料
        Integer employeeId = employeeService.createEmployee(employee);

        //為 Employee 添加預設的 Role
        Role normalRole = roleService.getRoleByName("ROLE_NORMAL_EMPLOYEE");
        employeeService.addRoleForEmployeeId(employeeId, normalRole);

        return "註冊成功";
    }

//    @PostMapping("/employeeLogin")
//    public String userLogin(Authentication authentication) {
//        //帳號密碼驗證由 Spring Security 處理，能執行到這裡表示已經登入成功
//        //取得使用者帳號
//        String username = authentication.getName();
//
//        //取得使用者權限
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        return "登入成功！員工名稱： " + username + " 的權限為： " + authorities;
//    }

    @PostMapping("/employeeLogin")
    public ResponseEntity<Map<String, Object>> userLogin(Authentication authentication) {
        if (authentication == null) {
            // 驗證失敗，返回 401
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("message", "登入失敗！");
            return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);
        }


        //帳號密碼驗證由 Spring Security 處理，能執行到這裡表示已經登入成功
        //取得使用者帳號
        String username = authentication.getName();

        //取得使用者權限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Object object = authentication.getPrincipal();
        JSONObject obj = new JSONObject();
        if(object instanceof UserDetails){
                     obj
                    .put("name", ((UserDetails) object).getUsername())
                    .put("password", ((UserDetails) object).getPassword())
                    .put("Authority", ((UserDetails) object).getAuthorities());
            String token = jsonWebTokenUtility.createToken(obj.toString(), 3600L);
            obj.put("token", token);
        }

        // 構建回應的主體
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "登入成功！");
        responseBody.put("username", username);
        responseBody.put("authorities", authorities);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json");
        headers.set("Authorization", obj.getString("token"));

        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }
}
