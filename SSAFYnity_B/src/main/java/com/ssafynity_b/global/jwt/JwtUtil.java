package com.ssafynity_b.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    //JWT 생성
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
                .signWith(jwtConfig.getSecretKey()) //새로운 방식
                .compact();
    }

    //JWT 검증 및 사용자명 추출
    public String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey()) //새로운 방식
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); //사용자 명 추출
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
