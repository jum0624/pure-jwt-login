package com.example.purejwtlogin.common.contextholder;

public class SecurityContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static String getToken() {
        return contextHolder.get();
    }

    public static void setToken(String token) {
        contextHolder.set(token);
    }
}
