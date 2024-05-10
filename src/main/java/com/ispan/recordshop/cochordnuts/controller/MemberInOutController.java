package com.ispan.recordshop.cochordnuts.controller;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
// @RequestMapping("/ajax/secure")
public class MemberInOutController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/login")
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
			responseJson.put("message", "登入失敗");
		} else {
			// Date currentLoginTime = new Date();
			// Date lastLoginTime = member.getLastLoginTime();
			// member.setLastLoginTime(currentLoginTime);
			// memberService.logout(member);
			session.setAttribute("user", member);
			responseJson.put("success", true);
			responseJson.put("message", "登入成功");
			// responseJson.put("last_login_time", lastLoginTime);
		}
		return responseJson.toString();
	}

	// 登出
	@GetMapping("/users/logout")
	public String logoutAction(HttpServletRequest request, HttpSession session) {

		Member member = (Member) session.getAttribute("loggedInUser");

		if (member != null) {
			member.setLastLoginTime(new Date());

			memberService.logout(member);
		}

		session.invalidate();

		return "users/logoutPage";
	}
}
