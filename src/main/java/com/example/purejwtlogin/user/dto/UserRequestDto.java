package com.example.purejwtlogin.user.dto;

import lombok.Builder;
import lombok.Getter;

public class UserRequestDto {
    @Getter
    public static class LoginRequestDto {
        private String username;
        private String password;
    }

    @Getter
    @Builder
    public static class TokenRequestDto {
        private String username;
        private String email;
    }
}
