package com.example.purejwtlogin.common.interceptor;

import com.example.purejwtlogin.common.contextholder.SecurityContextHolder;
import com.example.purejwtlogin.common.security.ExcludedUri;
import com.example.purejwtlogin.common.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (ExcludedUri.isExcludeURI(request)) {
            return true;
        }

        // Token 검증
        String token = jwtProvider.getAccessTokenCookie();

        if (!jwtProvider.validateToken(token)) {
            return false;
        }


        SecurityContextHolder.setToken(token);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityContextHolder.clearContext();
    }
}
