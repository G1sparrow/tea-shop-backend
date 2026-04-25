package com.teashop.common.filter;

import com.teashop.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 检查Authorization头是否存在且格式正确
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                logger.error("Error extracting username from token: {}", e.getMessage());
            }
        }

        // 如果token有效且用户未被认证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 验证token
            if (jwtUtil.validateToken(token, username)) {
                // 创建用户详情
                UserDetails userDetails = User.builder()
                        .username(username)
                        .password("")
                        .roles("ADMIN") // 可以根据实际情况设置角色
                        .build();

                // 创建认证令牌
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // 设置认证详情
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 设置认证上下文
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
