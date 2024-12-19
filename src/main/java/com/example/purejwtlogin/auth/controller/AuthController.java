package com.example.purejwtlogin.auth.controller;

import com.example.purejwtlogin.auth.service.AuthService;
import com.example.purejwtlogin.common.security.JwtProvider;
import com.example.purejwtlogin.user.dto.UserRequestDto;
import com.example.purejwtlogin.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto.LoginRequestDto dto) {
        return authService.login(dto);
    }

    @GetMapping("/validate")
    public boolean validate() {
        return authService.validate();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("logout");
    }
}
