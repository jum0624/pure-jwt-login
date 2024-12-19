package com.example.purejwtlogin.common.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
@Getter
public enum ExcludedUri {

    LOGIN(HttpMethod.POST, "/auth/login"),
    ;

    private final HttpMethod method;
    private final String uri;

    public static boolean isExcludeURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        for (ExcludedUri target: ExcludedUri.values()) {
            if (target.uri.equals(uri) && target.method.equals(method)) {
                return true;
            }
        }

        return false;
    }
}