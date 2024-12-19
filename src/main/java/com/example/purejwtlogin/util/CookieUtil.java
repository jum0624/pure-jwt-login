package com.example.purejwtlogin.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CookieUtil {

    private static final int DEFAULT_MAX_AGE = 3600; // 쿠키 유효 시간 (초 단위)
    private static final String COOKIE_PATH = "/";  // 쿠키 경로 설정

    // 현재 HttpServletResponse 가져오기
    private static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getResponse();
        }
        throw new IllegalStateException("No HttpServletResponse found in current context.");
    }

    // 현재 HttpServletRequest 가져오기
    private static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        throw new IllegalStateException("No HttpServletRequest found in current context.");
    }



    public static void createCookie(String name, String value) {
        createCookie(name, value, DEFAULT_MAX_AGE);
    }
    // 쿠키 생성 및 추가
    public static void createCookie(String name, String value, int maxAge) {
        HttpServletResponse response = getCurrentResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);  // JavaScript로 접근 불가
        cookie.setSecure(true);   // HTTPS 에서만 전송 (배포 시 활성화)
        cookie.setPath(COOKIE_PATH); // 애플리케이션 전역에서 사용 가능
        cookie.setMaxAge(maxAge); // 쿠키 유효 시간 설정
        response.addCookie(cookie);
    }

    // 쿠키 읽기
    public static String getCookie(String name) {
        HttpServletRequest request = getCurrentRequest();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue(); // 쿠키 값 반환
                }
            }
        }
        return null; // 쿠키가 없으면 null 반환
    }

    // 쿠키 삭제
    public static void deleteCookie(String name) {
        HttpServletResponse response = getCurrentResponse();
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(0); // 유효 시간 0으로 설정
        response.addCookie(cookie);
    }
}