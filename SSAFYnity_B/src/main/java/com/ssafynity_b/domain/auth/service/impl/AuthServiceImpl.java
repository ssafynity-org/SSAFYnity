package com.ssafynity_b.domain.auth.service.impl;

import com.ssafynity_b.domain.auth.dto.LoginRequest;
import com.ssafynity_b.domain.auth.dto.LoginResponse;
import com.ssafynity_b.domain.auth.service.AuthService;
import com.ssafynity_b.global.jwt.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        //ID,비밀번호 검증 로직
        if ("dldnwls009@gmail.com".equals(loginRequest.getEmail()) && "1234".equals(loginRequest.getPassword())) {
            // 토큰 생성
            String jwtToken = jwtUtil.generateToken(loginRequest.getEmail());
            return LoginResponse.builder()
                    .jwtToken(jwtToken)
                    .build();
        }
        return null; // 인증 실패 시 null 반환
    }
}
