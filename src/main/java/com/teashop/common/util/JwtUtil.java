package com.teashop.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    //生成密钥
    @Autowired
    private Environment env;
    private SecretKey getSecretKey() {
        String secret = env.getProperty("jwt.secret", "teashopSecretKey");
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 从token中获取用户名
    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    // 从token中获取用户ID
    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    // 生成token
    public String generateToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(userId.toString()) // 使用用户ID作为subject
                .claim("username", username) // 将用户名作为声明存储
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10小时
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // ✅ 安全
                .compact();
    }

    // 验证token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 从token中提取特定声明
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 获取所有声明
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查token是否过期
    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    // 获取过期时间
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}