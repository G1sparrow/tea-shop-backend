package com.teashop.common.config;

import com.teashop.common.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 允许访问前端页面和静态资源
                .requestMatchers("/").permitAll()
                .requestMatchers("/*.html").permitAll() // 允许访问HTML文件
                .requestMatchers("/static/**").permitAll() // 允许访问静态资源
                .requestMatchers("/assets/**").permitAll() // 允许访问前端资源
                .requestMatchers("/favicon.ico").permitAll() // 允许访问图标
                
                // 允许所有用户访问用户产品相关的API
                .requestMatchers("/api/user/products/**").permitAll()
                .requestMatchers("/api/user/products/category/**").permitAll()
                .requestMatchers("/api/user/products/search").permitAll()
                
                // 允许用户注册和登录
                .requestMatchers("/api/user/register").permitAll()
                .requestMatchers("/api/user/login").permitAll()
                // /api/user/me 需要通过JWT认证，由JwtAuthenticationFilter处理

                //允许管理员登录
                .requestMatchers("/api/admin/login").permitAll()
                // 管理员API需要ADMIN角色
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // 用户API需要USER角色或已认证
                .requestMatchers("/api/user/**").authenticated()
                .anyRequest().permitAll() // 其他非API请求允许访问
            )
            .csrf(csrf -> csrf.disable()) // 如果是API应用，通常禁用CSRF
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}