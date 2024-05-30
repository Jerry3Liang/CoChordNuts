package com.ispan.recordshop.cochordnuts.security;

import com.ispan.recordshop.cochordnuts.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )

                .csrf(csrf -> csrf.disable())

                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginProcessingUrl("/rest/employeeLogin")
                        .usernameParameter("name")
                        .successHandler(myAuthenticationSuccessHandler)
                        .failureHandler(myAuthenticationFailureHandler)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/rest/employeeLogout")
                        .logoutSuccessHandler((res, resp, e) -> {
                            resp.setContentType("application/json;charset=UTF-8");
                            PrintWriter out = resp.getWriter();
                            out.write("登出成功");
                            out.flush();
                            out.close();
                        })
                        .deleteCookies()
                        .permitAll())
//                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        //員工註冊帳號功能
                        .requestMatchers("/rest/register").permitAll()

                        //員工登入功能
                        .requestMatchers("/rest/employeeLogin").permitAll()

                        //會員
                        .requestMatchers("/products/email/{email}").permitAll()
                        .requestMatchers("/members").permitAll()
                        .requestMatchers("/members/{pk}").permitAll()
                        .requestMatchers("/member/changepwd/{pk}").permitAll()
                        .requestMatchers("/members/{pk}").permitAll()
                        .requestMatchers("/members/find").permitAll()
                        .requestMatchers("/members/{pk}").permitAll()
                        .requestMatchers("/members/case/{pk}").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()

                        //產品
                        .requestMatchers("/products/create").permitAll()
                        .requestMatchers("/products/modify/{id}").permitAll()
                        .requestMatchers("/products/remove/{id}").permitAll()
                        .requestMatchers("/products/photo/{id}").permitAll()
                        .requestMatchers("/products/search").permitAll()
                        .requestMatchers("/products/searchCount").permitAll()
                        .requestMatchers("/products/findAll").permitAll()
                        .requestMatchers("/products/detail/{id}").permitAll()
                        .requestMatchers("/products/detailProduct/{id}").permitAll()
                        .requestMatchers("/products/isBest").permitAll()
                        .requestMatchers("/products/isDiscount").permitAll()
                        .requestMatchers("/products/isPreorder").permitAll()
                        .requestMatchers("/products/languageFind/{languageNo}").permitAll()
                        .requestMatchers("/products/styleFind").permitAll()

                        //訂單
                        .requestMatchers("/orders/insert/{MemberNo}").permitAll()
                        .requestMatchers("/orders/update/{MemberNo}").permitAll()
                        .requestMatchers("/orders/deleteById/{odNo}").permitAll()
                        .requestMatchers("/orders/findAll").permitAll()
                        .requestMatchers("/orders/findByOrderNo/{odNo}").permitAll()
                        .requestMatchers("/orders/findBymemberNo").permitAll()
                        .requestMatchers("/orders/findCartByMemberNo/{memberNo}").permitAll()
                        .requestMatchers("/orderDetail/deleteById/{orderDetailNo}").permitAll()
                        .requestMatchers("/orderDetail/findByOdNo/{odNo}").permitAll()
                        .requestMatchers("/orderDetail/findAll").permitAll()
                        .requestMatchers("/orderDetail/update/{orderDetailNo}").permitAll()
                        .requestMatchers("/orderDetail/insert").permitAll()

                        //客服功能
                        .requestMatchers("/rest/allCase").permitAll()
                        .requestMatchers("/findMemberCase/{memberNo}").permitAll()
                        .requestMatchers("/rest/createCustomerCase").permitAll()
                        .requestMatchers("/rest/findCase/{pk}").permitAll()
                        .requestMatchers("/rest/customerCase/{pk}").permitAll()
                        .requestMatchers("/rest/customerCaseDelete/{pk}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/rest/allAnswer").permitAll()
                        .requestMatchers("/rest/Answer").permitAll()
                        .requestMatchers("/rest/findContent/{pk}").permitAll()
                        .requestMatchers("/rest/findCaseContent/{caseNo}").permitAll()
                        .requestMatchers("/rest/Answer/{pk}").permitAll()



                        .anyRequest().permitAll()
                )

                .cors(cors -> cors
                        .configurationSource(createCorsConfig())
                )

                .build();
    }

    private CorsConfigurationSource createCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));

//        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
