package com.ispan.recordshop.cochordnuts.controller;

import com.ispan.recordshop.cochordnuts.model.Employee;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.service.EmployeeService;
import com.ispan.recordshop.cochordnuts.service.RoleService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import com.ispan.recordshop.cochordnuts.util.JsonWebTokenUtility;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//        //為 Employee 添加預設的 Role
//        Role normalRole = roleService.getRoleByName("ROLE_NORMAL_EMPLOYEE");
//        employeeService.addRoleForEmployeeId(employeeId, normalRole);

        return "註冊成功";
    }

//    @PostMapping("/employeeLogin")
//    public String userLogin(Authentication authentication) {
//        //帳號密碼驗證由 Spring Security 處理，能執行到這裡表示已經登入成功
//        //取得使用者帳號
//        String username = authentication.getName();
//        System.out.println(username);
//
//        //取得使用者權限
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        return "登入成功！員工名稱： " + username + " 的權限為： " + authorities;
//    }

//    @PostMapping("/employeeLogin")
//    public ResponseEntity<Map<String, Object>> userLogin(Authentication authentication) {
//        if (authentication == null) {
//            // 驗證失敗，返回 401
//            Map<String, Object> errorBody = new HashMap<>();
//            errorBody.put("message", "登入失敗！");
//            return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);
//        }
//
//
//        //帳號密碼驗證由 Spring Security 處理，能執行到這裡表示已經登入成功
//        //取得使用者帳號
//        String username = authentication.getName();
//
//        //取得使用者權限
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        // 構建回應的主體
//        Map<String, Object> responseBody = new HashMap<>();
//        responseBody.put("message", "登入成功！");
//        responseBody.put("username", username);
//        responseBody.put("authorities", authorities);
//
//
//        return new ResponseEntity<>(responseBody, HttpStatus.OK);
//    }

    @PostMapping("/employeeLogin")
    public String handlerMethod(HttpSession session, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        // 接收資料
        JSONObject obj = new JSONObject(json);
        String name = obj.isNull("empName") ? null : obj.getString("empName");
        String password = obj.isNull("password") ? null : obj.getString("password");

        // 驗證資料
        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            responseJson.put("success", false);
            responseJson.put("message", "請輸入帳號與密碼");
            return responseJson.toString();
        }

        // 呼叫model
        Employee employee = employeeService.login(name, password);

        // 根據model執行結果，導向view
        if (employee == null) {
            responseJson.put("success", false);
            responseJson.put("message", "請檢查帳號和密碼");
        } else {
            String lastLoginTime = DatetimeConverter.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
            String loginTime = DatetimeConverter.toString(employee.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss");

            session.setAttribute("loginMember", employee);
            session.setAttribute("lastLoginTime", lastLoginTime);
            responseJson.put("success", true);
            responseJson.put("message", "登入成功");
            responseJson.put("lastLoginTime", lastLoginTime);
            responseJson.put("loginTime", loginTime);
        }

        JSONObject user = new JSONObject()
                .put("employeeNo", employee.getEmployeeNo())
                .put("empName", employee.getName());

        String lastLoginTime = DatetimeConverter.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        String loginTime = DatetimeConverter.toString(employee.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss");
        String token = jsonWebTokenUtility.createEncryptedToken(user.toString(), null);
        responseJson.put("token", token);
        responseJson.put("name", employee.getName());
        responseJson.put("user", employee);
        responseJson.put("employeeNo", employee.getEmployeeNo());
        responseJson.put("lastLoginTime", lastLoginTime);
        responseJson.put("loginTime", loginTime);

        return responseJson.toString();
    }

    @PostMapping("/employeeLogout")
    public String logout(HttpSession session, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        Integer employeeNo = obj.isNull("employeeNo") ? null : obj.getInt("employeeNo");

        if (employeeNo == null) {
            responseJson.put("success", false);
            responseJson.put("message", "用戶未登錄");
            return responseJson.toString();
        }

        Employee updatedEmployee = employeeService.logout(json);

        if (updatedEmployee != null) {
            session.removeAttribute("loginMember");
            session.removeAttribute("lastLoginTime");

            responseJson.put("success", true);
            responseJson.put("message", "登出成功");
        }
        return responseJson.toString();
    }
}
