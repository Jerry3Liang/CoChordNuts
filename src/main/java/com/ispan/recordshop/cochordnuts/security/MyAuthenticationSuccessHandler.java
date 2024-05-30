package com.ispan.recordshop.cochordnuts.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.recordshop.cochordnuts.dto.PayLoadDto;
import com.ispan.recordshop.cochordnuts.util.JWTUtil;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> authoritiesList = new ArrayList<>(authorities.size());
            authorities.forEach(authority -> {
                authoritiesList.add(authority.getAuthority());
            });

            Date now = new Date();
            Date exp = DateUtil.offsetSecond(now, 60*60);
            PayLoadDto payLoadDto = PayLoadDto.builder()
                    .sub(user.getUsername())
                    .iat(now.getTime())
                    .exp(exp.getTime())
                    .jti(UUID.randomUUID().toString())
                    .username(user.getUsername())
                    .authorities(authoritiesList)
                    .build();
            try {
                String token = JWTUtil.generateTokenByHMAC(
                        //nimbus-jose-jwt 所使用的 HMAC SHA256 演算法
                        //所需金鑰長度至少要 256位元 (32位元組)，因此先用 md5 加密
                        JSONUtil.toJsonStr(payLoadDto),
                        SecureUtil.md5(JWTUtil.DEFAULT_SECRET)
                );
                response.setHeader("Authorization", token);
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();
                System.out.println(principal);
                out.write(new ObjectMapper().writeValueAsString(principal));
                out.write("登入成功");
                out.flush();
                out.close();
            } catch (JOSEException e) {
                e.printStackTrace();
            }
        }
    }
}
