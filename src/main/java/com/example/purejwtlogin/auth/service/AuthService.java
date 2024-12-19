package com.example.purejwtlogin.auth.service;

import com.example.purejwtlogin.common.contextholder.SecurityContextHolder;
import com.example.purejwtlogin.common.security.JwtProvider;
import com.example.purejwtlogin.user.domain.User;
import com.example.purejwtlogin.user.dto.UserRequestDto;
import com.example.purejwtlogin.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtProvider jwtProvider;

    public UserResponseDto login(UserRequestDto.LoginRequestDto dto) {
        // id, pw 검증
        if (!validPassword(dto.getUsername(), dto.getPassword())) {
            throw new RuntimeException("로그인실패");
        }

        User user = User.builder()
                .id(1L)
                .username("test")
                .email("test@test.com").build();

        UserRequestDto.TokenRequestDto userDto = UserRequestDto.TokenRequestDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        // accessToken 발급
        String accessToken = jwtProvider.createAccessToken(userDto);

        jwtProvider.setAccessTokenCookie(accessToken);

        return UserResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public void logout() {
        jwtProvider.removeAccessTokenCookie();
    }

    public boolean validate() {
        if (!jwtProvider.validateToken(jwtProvider.getAccessTokenCookie())) {
            return false;
        }

        log.info("User Info===> " + jwtProvider.getSubject());

        return true;
    }

    private boolean validPassword(String username, String password) {
        return true;
    }
}
