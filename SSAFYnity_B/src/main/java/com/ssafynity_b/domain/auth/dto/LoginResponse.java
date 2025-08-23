package com.ssafynity_b.domain.auth.dto;

import lombok.*;
import org.springframework.http.ResponseCookie;

@ToString
@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private String accessToken;

    private ResponseCookie refreshToken;

}
