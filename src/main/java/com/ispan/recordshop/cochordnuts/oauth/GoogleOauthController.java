package com.ispan.recordshop.cochordnuts.oauth;

import com.ispan.recordshop.cochordnuts.dao.MemberDao;
import com.ispan.recordshop.cochordnuts.dao.MemberDaoImpl;
import com.ispan.recordshop.cochordnuts.dto.MemberDTO;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.service.EmployeeService;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import com.ispan.recordshop.cochordnuts.util.JsonWebTokenUtility;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class GoogleOauthController {

    private final String GOOGLE_CLIENT_ID = "325692365134-0pr3latp9i2bcjjk7qmdsdt9v5r31uuk.apps.googleusercontent.com";
    String GOOGLE_CLIENT_SECRET = "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";
    String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDaoImpl memberDaoImpl;

    //拼接跳轉的 url，將使用者跳轉到 Google 認證中心頁面
    @GetMapping("/google/buildAuthUrl")
    public String buildAuthUrl() {

        // 拼接 Google 所提供的 auth url 的請求參數
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(GOOGLE_AUTH_URL)
                .queryParam("response_type", "code")
                .queryParam("client_id", GOOGLE_CLIENT_ID)
                .queryParam("scope", "profile+email+openid")
                .queryParam("redirect_uri", "http://192.168.31.96:4173")
                .queryParam("state", generateRandomState())
                .queryParam("access_type", "offline");

        return uriBuilder.toUriString();
    }

    //傳遞 code、client_id、client_secret 的值給 Google 認證中心，換取 access_token 的值
    @PostMapping("/google/exchangeToken")
    public String exchangeToken(@RequestBody ExchangeTokenRequest exchangeTokenRequest) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        //填寫 request body 中的請求參數
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", GOOGLE_CLIENT_ID);
        body.add("client_secret", GOOGLE_CLIENT_SECRET);
        body.add("code", exchangeTokenRequest.getCode());
        body.add("redirect_uri", "http://192.168.31.96:4173");

        //發送請求
        String result;
        try {
            result = restTemplate.postForObject(
                    //使用 Google 所提供的 token url，傳遞 code 的值過去，即可取得使用者的 access_token
                    GOOGLE_TOKEN_URL,
                    new HttpEntity<>(body, headers),
                    String.class
            );
        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }

    //使用 access_token，取得使用者在 Google 中的資料
    @GetMapping("/google/getGoogleUser")
    public String getGoogleUser(@RequestParam String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
//        headers.setBearerAuth(accessToken);

        //call Google 的 api，取得使用者在 Google 中的基本資料
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";

        //發送請求
        String result;
        try {
            result = restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            String.class
                    )
                    .getBody();

        } catch (Exception e) {
            result = e.toString();
        }


        JSONObject obj = new JSONObject(result);
        String email = obj.getString("email");
        String name = obj.getString("name");


        boolean existEmail = memberService.existByEmail(email);
        if(!existEmail){
            MemberDTO memberDto = new MemberDTO();
            memberDto.setName(name);
            memberDto.setEmail(email);
            memberDto.setBirthday(DatetimeConverter.toString(new Date(), "yyyy-MM-dd"));
            memberDto.setRegisterTime(DatetimeConverter.toString(new Date(), "yyyy-MM-dd"));
            memberDto.setLastLoginTime(DatetimeConverter.toString(new Date(), "yyyy-MM-dd"));
            Member updateMember = memberService.createGoogle(memberDto);
            obj.put("memberNo", updateMember.getMemberNo());
            obj.put("isGoogleLogin", true);
            obj.put("lastLoginTime", DatetimeConverter.toString(updateMember.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            MemberDTO member = memberDaoImpl.findMemberByEmail(email);
            obj.put("memberNo", member.getMemberNo());
            obj.put("isGoogleLogin", true);
            obj.put("lastLoginTime", member.getLastLoginTime());
        }

        return obj.toString();
    }

    @PostMapping("/google/logout")
    public String googleLogout(HttpSession session, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");
        String lastLoginTime = obj.isNull("lastLoginTime") ? null : obj.getString("lastLoginTime");

        if (memberNo == null) {
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

    private String generateRandomState() {
        SecureRandom sr = new SecureRandom();
        byte[] data = new byte[6];
        sr.nextBytes(data);
        return Base64.getUrlEncoder().encodeToString(data);
    }
}
