package com.ispan.recordshop.cochordnuts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.MemberDTO;
import com.ispan.recordshop.cochordnuts.model.CustomerCase;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;

@RestController
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/sendCode")
    public Map<String, Object> sendVerificationCode(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String email = request.get("email");
        String code = request.get("code");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("CoChordNuts註冊驗證信");
        message.setText("您好，感謝您在CoChordsNuts的註冊申請 !\r\n" +
                "您的驗證碼是: " + code + "\r\n\r\n" + "如果這不是您本人的操作，請忽略本信件 !");
        mailSender.send(message);

        response.put("success", true);
        response.put("message", "驗證碼已發送至您的Email!");
        return response;
    }

    // 帳號是否存在
    @GetMapping("/members/email/{email}")
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

    // 電話是否存在
    @GetMapping("/members/phone/{phone}")
    public String existsByPhone(@PathVariable("phone") String phone) {
        JSONObject responseJson = new JSONObject();
        boolean exist = memberService.existByPhone(phone);
        if (exist) {
            responseJson.put("success", false);
            responseJson.put("message", "電話已存在");
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "電話不存在");
        }
        return responseJson.toString();
    }

    // 新增
    @PostMapping("/members")
    public ResponseEntity<?> createMember(@RequestBody MemberDTO memberRequest) {
        JSONObject responseJson = new JSONObject();

        if (memberRequest.getName().isBlank() || memberRequest.getBirthday().isBlank() ||
                memberRequest.getPassword().isBlank() || memberRequest.getEmail().isBlank() ||
                memberRequest.getPhone().isBlank()) {

            responseJson.put("success", false);
            if (memberRequest.getName().isBlank()) {
                responseJson.put("message", "姓名是必要欄位");
            } else if (memberRequest.getBirthday().isBlank()) {
                responseJson.put("message", "生日是必填欄位");
            } else if (memberRequest.getPassword().isBlank()) {
                responseJson.put("message", "密碼是必填欄位");
            } else if (memberRequest.getEmail().isBlank()) {
                responseJson.put("message", "Email是必填欄位");
            } else if (memberRequest.getPhone().isBlank()) {
                responseJson.put("message", "電話號碼是必填欄位");
            }
        } else {
            boolean emailExists = memberService.existByEmail(memberRequest.getEmail());
            boolean phoneExists = memberService.existByPhone(memberRequest.getPhone());

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
                Member member = memberService.create(memberRequest);
                if (member == null) {
                    responseJson.put("success", false);
                    responseJson.put("message", "新增失敗");
                } else {
                    responseJson.put("success", true);
                    responseJson.put("message", "新增成功");
                }
            }
        }
        return ResponseEntity.ok(responseJson.toString());
    }
    // @PostMapping("/members")
    // public String create(@RequestBody String json, List<Integer> favoriteIds) {
    // JSONObject responseJson = new JSONObject();
    // JSONObject obj = new JSONObject(json);
    // String name = obj.isNull("name") ? null : obj.getString("name");
    // String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
    // String password = obj.isNull("password") ? null : obj.getString("password");
    // String phone = obj.isNull("phone") ? null : obj.getString("phone");
    // String email = obj.isNull("email") ? null : obj.getString("email");

    // if (name.isBlank() || birthday.isBlank() || password.isBlank() ||
    // email.isBlank() || phone.isBlank()) {
    // responseJson.put("success", false);
    // if (name.isBlank()) {
    // responseJson.put("message", "姓名是必要欄位");
    // } else if (birthday.isBlank()) {
    // responseJson.put("message", "生日是必填欄位");
    // } else if (password.isBlank()) {
    // responseJson.put("message", "密碼是必填欄位");
    // } else if (email.isBlank()) {
    // responseJson.put("message", "Email是必填欄位");
    // } else if (phone.isBlank()) {
    // responseJson.put("message", "電話號碼是必填欄位");
    // }
    // } else {
    // boolean emailExists = memberService.existByEmail(email);
    // boolean phoneExists = memberService.existByPhone(phone);

    // if (emailExists || phoneExists) {
    // responseJson.put("success", false);
    // if (emailExists && phoneExists) {
    // responseJson.put("message", "Email和電話號碼已存在");
    // } else if (emailExists) {
    // responseJson.put("message", "Email已存在");
    // } else {
    // responseJson.put("message", "電話號碼已存在");
    // }
    // } else {
    // Member member = memberService.create(json, favoriteIds);
    // if (member == null) {
    // responseJson.put("success", false);
    // responseJson.put("message", "新增失敗");
    // } else {
    // responseJson.put("success", true);
    // responseJson.put("message", "新增成功");
    // }
    // }
    // }
    // return responseJson.toString();
    // }

    // 修改
    @PutMapping("/members/{pk}")
    public String modify(@PathVariable(name = "pk") Integer memberNo, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        if (memberNo == null) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo是必要欄位");
        } else if (!memberService.existById(memberNo)) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo不存在");
        } else {
            JSONObject obj = new JSONObject(json);
            String phone = obj.isNull("phone") ? null : obj.getString("phone");
            String email = obj.isNull("email") ? null : obj.getString("email");

            String oriPhone = memberService.getPhoneById(memberNo);
            String oriEmail = memberService.getEmailById(memberNo);

            // 若email和phone都沒有改變，則進行其他項目的修改
            if (Objects.equals(phone, oriPhone) && Objects.equals(email, oriEmail)) {
                Member member = memberService.modify(json);
                if (member == null) {
                    responseJson.put("success", false);
                    responseJson.put("message", "修改失敗");
                } else {
                    responseJson.put("success", true);
                    responseJson.put("message", "修改成功");
                    responseJson.put("userName", member.getName());
                }
            } else {
                boolean phoneExists = phone != null && memberService.existByPhone(phone);
                boolean emailExists = email != null && memberService.existByEmail(email);

                // 若新的電話和email都已存在
                if (!Objects.equals(phone, oriPhone) && !Objects.equals(email, oriEmail) && phoneExists
                        && emailExists) {
                    responseJson.put("success", false);
                    responseJson.put("message", "新的電話號碼和email都已存在");
                } else if (Objects.equals(email, oriEmail) && phoneExists) {
                    // 若新的電話已存在
                    responseJson.put("success", false);
                    responseJson.put("message", "新的電話號碼已存在");
                } else if (Objects.equals(phone, oriPhone) && emailExists) {
                    // 若新的email已存在
                    responseJson.put("success", false);
                    responseJson.put("message", "新的email已存在");
                } else {
                    // 其他情況下，進行修改
                    System.out.println("hahahahhahah");
                    Member member = memberService.modify(json);
                    if (member == null) {
                        responseJson.put("success", false);
                        responseJson.put("message", "修改失敗");
                    } else {
                        responseJson.put("success", true);
                        responseJson.put("message", "修改成功");
                        responseJson.put("userName", member.getName());
                    }
                }
            }
        }
        return responseJson.toString();
    }

    @PutMapping("/member/updateRecipient/{pk}")
    public String updateRecipient(@PathVariable(name = "pk") Integer memberNo, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        if (memberService.modifyRecipient(memberNo, json) != null) {
            responseJson.put("success", true);
            return responseJson.toString();
        } else {
            responseJson.put("success", false);
            return responseJson.toString();
        }

    }

    // 修改密碼
    @PutMapping("/member/changepwd/{pk}")
    public String modifyPwd(@PathVariable(name = "pk") Integer memberNo, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        String oriPassword = obj.isNull("oriPassword") ? null : obj.getString("oriPassword");
        String password = obj.isNull("password") ? null : obj.getString("password");

        if (memberNo == null || oriPassword == null || password == null) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo和password是必要欄位");
        } else if (!memberService.existById(memberNo)) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo不存在");
        } else {
            Member member = memberService.modifyPwd(json);

            if (member == null) {
                responseJson.put("success", false);
                responseJson.put("message", "修改失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "修改成功");
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
            responseJson.put("message", "密碼是必要欄位");
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

     // 修改帳號狀態
    @PutMapping("/member/changeStatus/{pk}")
    public String changeStatus(@PathVariable(name = "pk") Integer memberNo) {
        JSONObject responseJson = new JSONObject();

        if (memberNo == null) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo和password是必要欄位");
        } else if (!memberService.existById(memberNo)) {
            responseJson.put("success", false);
            responseJson.put("message", "memberNo不存在");
        } else {
            Member member = memberService.changeStatus(memberNo);

            if (member == null) {
                responseJson.put("success", false);
                responseJson.put("message", "修改失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "修改成功");
            }
        }
        return responseJson.toString();
    }

    // 查詢多筆
    @PostMapping("/members/find")
    public String find(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();

        JSONArray array = new JSONArray();
        List<Member> members = memberService.find(json);
        if (members != null && !members.isEmpty()) {
            for (Member member : members) {
                String birthday = DatetimeConverter.toString(member.getBirthday(), "yyyy-MM-dd");
                String registerTime = DatetimeConverter.toString(member.getRegisterTime(), "yyyy-MM-dd HH:mm");
                String lastLoginTime = DatetimeConverter.toString(member.getLastLoginTime(), "yyyy-MM-dd HH:mm");

                JSONObject item = new JSONObject()
                        .put("memberNo", member.getMemberNo())
                        .put("name", member.getName())
                        .put("birthday", birthday)
                        .put("registerTime", registerTime)
                        .put("lastLoginTime", lastLoginTime)
                        .put("email", member.getEmail())
                        .put("address", member.getAddress())
                        .put("phone", member.getPhone())
                        .put("recipient", member.getRecipient())
                        .put("recipientAddress", member.getRecipientAddress())
                        .put("recipientPhone", member.getRecipientPhone())
                        .put("memberStatus", member.getMemberStatus());
                array.put(item);
            }
        }
        responseJson.put("list", array);

        long count = memberService.count(json);
        responseJson.put("count", count);

        return responseJson.toString();
    }

    // 查詢多筆訂單
    // @PostMapping("/members/findOr")
    // public String findOrders(@RequestBody String json) {
    // JSONObject responseJson = new JSONObject();

    // JSONArray array = new JSONArray();
    // List<Orders> orders = memberService.findOrders(json);
    // if (orders != null && !orders.isEmpty()) {
    // for (Orders order : orders) {

    // JSONObject item = new JSONObject()
    // .put("memberNo", order.getMemberNo())
    // .put("orderNo", order.getOrderNo());
    // array.put(item);
    // }
    // }
    // responseJson.put("list", array);

    // long count = memberService.countOrders(json);
    // responseJson.put("count", count);

    // return responseJson.toString();
    // }

    // 查詢單筆
    @GetMapping("/members/{pk}")
    public String findById(@PathVariable(name = "pk") Integer memberNo) {
        JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
        Member member = memberService.findById(memberNo);
        if (member != null) {
            String birthday = DatetimeConverter.toString(member.getBirthday(), "yyyy-MM-dd");
            String registerTime = DatetimeConverter.toString(member.getRegisterTime(), "yyyy-MM-dd HH:mm");
            String lastLoginTime = DatetimeConverter.toString(member.getLastLoginTime(), "yyyy-MM-dd HH:mm");

            JSONObject item = new JSONObject()
                    .put("memberNo", member.getMemberNo())
                    .put("name", member.getName())
                    .put("birthday", birthday)
                    .put("registerTime", registerTime)
                    .put("lastLoginTime", lastLoginTime)
                    .put("email", member.getEmail())
                    .put("address", member.getAddress())
                    .put("phone", member.getPhone())
                    .put("recipient", member.getRecipient())
                    .put("recipientAddress", member.getRecipientAddress())
                    .put("recipientPhone", member.getRecipientPhone())
                    .put("memberStatus", member.getMemberStatus());
            array.put(item);
        }
        responseJson.put("list", array);
        return responseJson.toString();
    }

    // 查詢會員訂單
    // @GetMapping("/members/order/{pk}")
    // public String findCaseByMemberNo(@PathVariable(name = "pk") Integer memberNo)
    // {
    // JSONObject responseJson = new JSONObject();
    // JSONArray array = new JSONArray();
    // List<Orders> orders = memberService.findByMemberNo(memberNo);
    // if (!orders.isEmpty()) {
    // for (Orders order : orders) {
    // JSONObject item = new JSONObject()
    // // .put("memberNo", order.getMemberNo())
    // .put("orderNo", order.getOrderNo());
    // array.put(item);
    // }
    // }
    // responseJson.put("list", array);
    // return responseJson.toString();
    // }

    // 查詢會員客服
    @GetMapping("/members/case/{pk}")
    public String findByMemberNo(@PathVariable(name = "pk") Integer memberNo) {
        JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
        List<CustomerCase> customerCases = memberService.findCaseByMemberNo(memberNo);
        if (!customerCases.isEmpty()) {
            for (CustomerCase case1 : customerCases) {
                JSONObject item = new JSONObject()
                        // .put("memberNo", order.getMemberNo())
                        .put("caseNo", case1.getCaseNO());
                array.put(item);
            }
        }
        responseJson.put("list", array);
        return responseJson.toString();
    }

}
