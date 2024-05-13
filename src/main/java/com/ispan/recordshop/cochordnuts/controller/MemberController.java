package com.ispan.recordshop.cochordnuts.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;

@RestController
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 帳號是否存在
    @GetMapping("/products/email/{email}")
    public String existsByEmail(@PathVariable("email") String email) {
        JSONObject responseJson = new JSONObject();
        boolean exist = memberService.existByEmail(email);
        if (exist) {
            responseJson.put("success", false);
            responseJson.put("message", "帳號已存在");
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "帳號不存在");
        }
        return responseJson.toString();
    }

    @PostMapping("/members")
    public String create(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        String name = obj.isNull("name") ? null : obj.getString("name");
        String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
        String password = obj.isNull("password") ? null : obj.getString("password");
        String phone = obj.isNull("phone") ? null : obj.getString("phone");
        String email = obj.isNull("email") ? null : obj.getString("email");

        if (name.isBlank() || birthday.isBlank() || password.isBlank() || email.isBlank() || phone.isBlank()) {
            responseJson.put("success", false);
            if (name.isBlank()) {
                responseJson.put("message", "姓名是必要欄位");
            } else if (birthday.isBlank()) {
                responseJson.put("message", "生日是必填欄位");
            } else if (password.isBlank()) {
                responseJson.put("message", "密碼是必填欄位");
            } else if (email.isBlank()) {
                responseJson.put("message", "Email是必填欄位");
            } else if (phone.isBlank()) {
                responseJson.put("message", "電話號碼是必填欄位");
            }
        } else {
            boolean emailExists = memberService.existByEmail(email);
            boolean phoneExists = memberService.existByPhone(phone);

            if (emailExists || phoneExists) {
                responseJson.put("success", false);
                if (emailExists && phoneExists) {
                    responseJson.put("message", "Email和電話號碼已存在");
                } else if (emailExists) {
                    responseJson.put("message", "Email已存在");
                } else {
                    responseJson.put("message", "電話號碼已存在");
                }
            } else {
                Member member = memberService.create(json);
                if (member == null) {
                    responseJson.put("success", false);
                    responseJson.put("message", "新增失敗");
                } else {
                    responseJson.put("success", true);
                    responseJson.put("message", "新增成功");
                }
            }
        }
        return responseJson.toString();
    }

}
