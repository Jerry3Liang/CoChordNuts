package com.ispan.recordshop.cochordnuts.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import com.ispan.recordshop.cochordnuts.util.JsonWebTokenUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
// @RequestMapping("/ajax/secure")
public class MemberInOutController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private JsonWebTokenUtility jsonWebTokenUtility;

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
