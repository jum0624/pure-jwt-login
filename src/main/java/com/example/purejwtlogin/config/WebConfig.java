package com.example.purejwtlogin.config;

import com.example.purejwtlogin.common.interceptor.JwtInterceptor;
import com.example.purejwtlogin.common.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public JwtInterceptor jwtInterceptor(JwtProvider jwtProvider) {
        return new JwtInterceptor(jwtProvider);
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor(jwtProvider()));
    }
}