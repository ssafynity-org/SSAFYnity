package com.ssafynity_b.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "이메일", example = "SwaggerAdmin@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

}
