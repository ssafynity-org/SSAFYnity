package com.ssafynity_b.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "로그인 요청을 위한 DTO. 이메일과 비밀번호를 포함합니다.")
@Getter
@Setter
public class LoginRequest {

    @Schema(description = "이메일", example = "SwaggerAdmin@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

}
