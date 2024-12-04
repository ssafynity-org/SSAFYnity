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
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
                .signWith(jwtConfig.getSecretKey()) //새로운 방식
                .compact();
    }

    //JWT 검증 및 MemberId 추출
    public String extractMemberId(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey()) //새로운 방식
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); //MemberId 추출
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
