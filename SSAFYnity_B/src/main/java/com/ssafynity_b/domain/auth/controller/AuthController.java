package com.ssafynity_b.domain.auth.controller;

import com.ssafynity_b.domain.auth.dto.LoginRequest;
import com.ssafynity_b.domain.auth.dto.LoginResponse;
import com.ssafynity_b.domain.auth.service.AuthService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import com.ssafynity_b.global.jwt.JwtConfig;
import com.ssafynity_b.global.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String,String> redisTemplate;

    public AuthController(JwtConfig jwtConfig, AuthService authService, JwtProvider jwtProvider, RedisTemplate redisTemplate) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
        this.redisTemplate = redisTemplate;
    }

    @Operation(summary = "사용자 로그인(로그인 성공 시 Jwt토큰 발급)")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticate(loginRequest);

        if (loginResponse != null) {
            System.out.println("보내줄 쿠키 : " + loginResponse.getRefreshToken().toString());

            return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE,loginResponse.getRefreshToken().toString())
                            .body(loginResponse.getAccessToken());
        }
        return ResponseEntity.status(401).body("Unauthorized"); // 인증 실패
    }

    @Operation(summary = "현재 로그인한 사용자 정보 반환")
    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        System.out.println(userDetails.toString());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "LocalStorage에 들어있는 Jwt토큰 유효성검사(사용자 재방문시 호출)")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyJwtToken(){
        // Spring Security가 자동으로 JWT를 파싱하고 유효성 검증을 수행
        // SecurityContextHolder에 인증 정보가 있으면 유효한 토큰으로 간주
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @Operation(summary = "Redis에서 리프레쉬토큰 확인 후, 엑세스 토큰 발급")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refresh") String refreshToken){
        System.out.println("들어온 리프레쉬 토큰 : " + refreshToken);
        if(!jwtProvider.isTokenValid(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = jwtProvider.extractMemberEmail(refreshToken);
        String savedToken = redisTemplate.opsForValue().get("refresh:"+email);

        if(savedToken!=null && savedToken.equals(refreshToken)) {
            System.out.println("리프레쉬 토큰 검증 성공");
            String newAccessToken = jwtProvider.generateAccessToken(email);
            System.out.println("새로발급되는 액세스토큰 : " + newAccessToken);
            return ResponseEntity.ok(newAccessToken);
        } else {
            System.out.println("리프레쉬 토큰 검증 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
