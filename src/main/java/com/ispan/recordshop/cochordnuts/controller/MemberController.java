package com.ispan.recordshop.cochordnuts.controller;

import java.util.Objects;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // 新增
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

    // 修改
    @PutMapping("/members/{pk}")
    public String modify(@PathVariable(name = "pk") String email, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        if (email == null) {
            responseJson.put("success", false);
            responseJson.put("message", "email是必要欄位");
        } else if (!memberService.existByEmail(email)) {
            responseJson.put("success", false);
            responseJson.put("message", "email不存在");
        } else {
            JSONObject obj = new JSONObject(json);
            String phone = obj.isNull("phone") ? null : obj.getString("phone");

            String oriPhone = memberService.getPhoneByEmail(email);

            if (Objects.equals(phone, oriPhone)) {

                Member member = memberService.modify(json);
                if (member == null) {
                    responseJson.put("success", false);
                    responseJson.put("message", "修改失敗");
                } else {
                    responseJson.put("success", true);
                    responseJson.put("message", "修改成功");
                }
            } else {

                boolean phoneExists = memberService.existByPhone(phone);
                if (phoneExists) {
                    responseJson.put("success", false);
                    responseJson.put("message", "新的電話號碼已存在");
                } else {

                    Member member = memberService.modify(json);
                    if (member == null) {
                        responseJson.put("success", false);
                        responseJson.put("message", "修改失敗");
                    } else {
                        responseJson.put("success", true);
                        responseJson.put("message", "修改成功");
                    }
                }
            }
        }
        return responseJson.toString();
    }

    // 刪除，需要確認密碼
    @DeleteMapping("/members/{pk}")
    public String remove(@PathVariable(name = "pk") Integer memberNo, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        String password = obj.isNull("password") ? null : obj.getString("password");

        if (memberNo == null || password == null || password.isEmpty()) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo 和 password 是必要欄位");
        } else if (!memberService.existById(memberNo)) {
            responseJson.put("success", false);
            responseJson.put("message", "會員不存在");
        } else {
            if (memberService.delete(memberNo, password)) {
                responseJson.put("success", true);
                responseJson.put("message", "刪除成功");
            } else {
                responseJson.put("success", false);
                responseJson.put("message", "密碼錯誤或刪除失敗");
            }
        }
        return responseJson.toString();
    }

}
