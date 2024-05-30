package com.ispan.recordshop.cochordnuts.security.filter;

import cn.hutool.crypto.SecureUtil;
import com.ispan.recordshop.cochordnuts.dto.PayLoadDto;
import com.ispan.recordshop.cochordnuts.util.JWTUtil;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        //如果請求標頭中有 token，則進行解析，並且設定認證資訊
        try{
            SecurityContextHolder.getContext()
                    .setAuthentication(getAuthentication(token));
            filterChain.doFilter(request, response);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
    }

    //驗證 token，並解析 token，傳回以使用者名稱及密碼所表示之經過身份驗證的主體權杖
    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws ParseException, JOSEException {
        PayLoadDto payLoadDto = JWTUtil.verifyTokenByHMAC(token, SecureUtil.md5(JWTUtil.DEFAULT_SECRET));
        String username = payLoadDto.getUsername();
        List<String> roles = payLoadDto.getAuthorities();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        if(username != null){
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }

        return null;
    }
}
