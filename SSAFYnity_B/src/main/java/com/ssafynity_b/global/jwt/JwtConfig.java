package com.ssafynity_b.global.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.expiration-time}")
    private long expirationTime;

    public Key getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)); //Base64로 디코딩한후, 알고리즘 암호화처리
    }

}
