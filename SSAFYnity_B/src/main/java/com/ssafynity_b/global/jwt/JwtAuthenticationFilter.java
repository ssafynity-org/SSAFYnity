package com.ssafynity_b.global.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Key key;

    public JwtAuthenticationFilter(Key key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Swagger 관련 경로, 회원가입,로그인 경로는 필터링하지 않음
        if (requestPath.startsWith("/swagger-ui") || requestPath.startsWith("/v3/api-docs") || requestPath.startsWith("/api/auth/login") || requestPath.startsWith("/api/member") && "POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response); // 다음 필터로 이동
            return;
        }

        String authHeader = request.getHeader("Authorization");

        System.out.println("[authHeader] : "+authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                String memberId = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody()
                        .getSubject();

                System.out.println("JWT 검증 성공. 사용자: " + memberId);

                if (memberId != null) {
                    // 인증 컨텍스트 설정 (필요 시 권한 추가)
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(memberId, null, List.of())
                    );
                }
            } catch (Exception e) {
                System.out.println("JWT 검증 실패: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 유효하지 않은 토큰 처리
                return;
            }
        } else {
            System.out.println("Authorization 헤더가 없거나 올바르지 않음.");
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}
