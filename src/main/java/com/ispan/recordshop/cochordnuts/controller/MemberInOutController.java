package com.ispan.recordshop.cochordnuts.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import com.ispan.recordshop.cochordnuts.util.JsonWebTokenUtility;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class MemberInOutController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private JsonWebTokenUtility jsonWebTokenUtility;

	@Autowired
	private JavaMailSender mailSender;

	// 重製密碼驗證信
	@PostMapping("/sendReCode")
	public Map<String, Object> sendReCode(@RequestBody Map<String, String> request) {
		Map<String, Object> response = new HashMap<>();
		String email = request.get("email");
		String code = request.get("code");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("CoChordNuts重設密碼驗證信");
		message.setText("您好!" +
				"您的重設密碼驗證碼是: " + code + "\r\n\r\n" + "如果這不是您本人的操作，請忽略本信件 !");
		mailSender.send(message);

		response.put("success", true);
		response.put("message", "驗證碼已發送至您的Email!");
		return response;
	}

	// 重製密碼
	@PutMapping("/member/repwd/{pk}")
	public String rePwd(@PathVariable(name = "pk") String email, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);
		String password = obj.isNull("password") ? null : obj.getString("password");

		if (email == null || password == null) {
			responseJson.put("success", false);
			responseJson.put("message", "Email和password是必要欄位");
		} else if (!memberService.existByEmail(email)) {
			responseJson.put("success", false);
			responseJson.put("message", "Email不存在");
		} else {
			Member member = memberService.rePassword(json);

			if (member == null) {
				responseJson.put("success", false);
				responseJson.put("message", "重設失敗");
			} else {
				responseJson.put("success", true);
				responseJson.put("message", "重設成功");
			}
		}
		return responseJson.toString();
	}

	// 登入
	@PostMapping("/memberLogin")
	public String handlerMethod(HttpSession session, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		// 接收資料
		JSONObject obj = new JSONObject(json);
		String email = obj.isNull("email") ? null : obj.getString("email");
		String password = obj.isNull("password") ? null : obj.getString("password");

		// 驗證資料
		if (email == null || email.length() == 0 || password == null || password.length() == 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入帳號與密碼");
			return responseJson.toString();
		}

		// 呼叫model
		Member member = memberService.login(email, password);

		// 根據model執行結果，導向view
		if (member == null) {
			responseJson.put("success", false);
			responseJson.put("message", "請檢查帳號和密碼");
		} else {
			String lastLoginTime = DatetimeConverter.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
			String loginTime = DatetimeConverter.toString(member.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss");

			session.setAttribute("loginMember", member);
			session.setAttribute("lastLoginTime", lastLoginTime);

			responseJson.put("success", true);
			responseJson.put("message", "登入成功");
			responseJson.put("lastLoginTime", lastLoginTime);
			responseJson.put("loginTime", loginTime);
			responseJson.put("memberStatus", member.getMemberStatus());
		}

		JSONObject user = new JSONObject()
				.put("memberNo", member.getMemberNo())
				.put("email", member.getEmail());

		String lastLoginTime = DatetimeConverter.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String loginTime = DatetimeConverter.toString(member.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss");
		String token = jsonWebTokenUtility.createEncryptedToken(user.toString(), null);
		responseJson.put("token", token);
		responseJson.put("userName", member.getName());
		responseJson.put("user", member);
		responseJson.put("memberNo", member.getMemberNo());
		responseJson.put("lastLoginTime", lastLoginTime);
		responseJson.put("loginTime", loginTime);
		responseJson.put("memberStatus", member.getMemberStatus());
		return responseJson.toString();
	}

	// 登出
	@PostMapping("/memberLogout")
	public String logout(HttpSession session, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);
		Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");
		String lastLoginTime = obj.isNull("lastLoginTime") ? null : obj.getString("lastLoginTime");

		if (memberNo == null || lastLoginTime == null) {
			responseJson.put("success", false);
			responseJson.put("message", "用戶未登錄");
			return responseJson.toString();
		}

		Member updatedMember = memberService.logout(json);

		if (updatedMember != null) {
			session.removeAttribute("loginMember");
			session.removeAttribute("lastLoginTime");

			responseJson.put("success", true);
			responseJson.put("message", "登出成功");
		}
		return responseJson.toString();
	}

}
