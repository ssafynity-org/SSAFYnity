package com.ssafynity_b.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtConfig jwtConfig;

    //JWT 생성
    public String generateAccessToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessExpirationTime()))
                .signWith(jwtConfig.getSecretKey()) //시크릿 키로 서명
                .compact();
    }

    //Refresh Token 생성
    public String generateRefreshToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpirationTime()))
                .signWith(jwtConfig.getSecretKey()) //시크릿 키로 서명
                .compact();
    }

    //JWT 검증 및 Member이메일 추출
    public String extractMemberEmail(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey()) //새로운 방식
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); //Member이메일 추출
    }

    //토큰 유효성 검사
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecretKey()) //새로운 방식
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch(Exception e){
            return false;
        }
    }


}
