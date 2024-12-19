package com.example.purejwtlogin.common.security;

import com.example.purejwtlogin.common.contextholder.SecurityContextHolder;
import com.example.purejwtlogin.user.dto.UserRequestDto;
import com.example.purejwtlogin.util.CookieUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String createAccessToken(UserRequestDto.TokenRequestDto dto) {
        // 현재 시간 및 만료 시간 설정
        long expirationTime = new Date().getTime() + 3600000; // 1시간(3600초)

        // JWT 생성 및 서명
        return Jwts.builder()
                .setSubject("jwtTokenTest") // 사용자 이름
                .setIssuedAt(new Date()) // 발급 시간
                .claim("username", dto.getUsername())
                .claim("email", dto.getEmail())
                .setExpiration(new Date(expirationTime)) // 만료 시간
                .signWith(getSignKey())
                .compact(); // JWT 문자열로 반환
    }

    public String getSubject() {
        String token = SecurityContextHolder.getToken();
        log.info(token);

        return Jwts.parserBuilder()
                        .setSigningKey(getSignKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // 유효한 토큰
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument token: " + e.getMessage());
        }
        return false; // 유효하지 않은 토큰
    }

    private Key getSignKey() {
        byte[] byteKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public void setAccessTokenCookie(String token) {
        CookieUtil.createCookie("accessToken", token);
    }

    public String getAccessTokenCookie() {
        return CookieUtil.getCookie("accessToken");
    }

    public void removeAccessTokenCookie() {
        CookieUtil.deleteCookie("accessToken");
    }
}
