package com.ssafynity_b.domain.auth.controller;

import com.ssafynity_b.domain.auth.dto.LoginRequest;
import com.ssafynity_b.domain.auth.dto.LoginResponse;
import com.ssafynity_b.domain.auth.service.AuthService;
import com.ssafynity_b.global.jwt.JwtConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(JwtConfig jwtConfig, AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "사용자 로그인(로그인 성공 시 Jwt토큰 발급)")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticate(loginRequest);
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse); // 인증 성공
        }
        return ResponseEntity.status(401).body("Unauthorized"); // 인증 실패
    }

    @Operation(summary = "LocalStorage에 들어있는 Jwt토큰 유효성검사(사용자 재방문시 호출)")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyJwtToken(){
        // Spring Security가 자동으로 JWT를 파싱하고 유효성 검증을 수행
        // SecurityContextHolder에 인증 정보가 있으면 유효한 토큰으로 간주
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            System.out.println("뭐냐이거 : "+ authentication);
            return ResponseEntity.ok("Token is valid");
        } else {
            System.out.println("왜터지냐??? : "+ authentication);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
