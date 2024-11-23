package com.ssafynity_b.domain.auth.controller;

import com.ssafynity_b.domain.auth.dto.LoginRequest;
import com.ssafynity_b.domain.auth.dto.LoginResponse;
import com.ssafynity_b.domain.auth.service.AuthService;
import com.ssafynity_b.global.jwt.JwtConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(JwtConfig jwtConfig, AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "JWT 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticate(loginRequest);
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse); // 인증 성공
        }
        return ResponseEntity.status(401).body("Unauthorized"); // 인증 실패
    }
}
