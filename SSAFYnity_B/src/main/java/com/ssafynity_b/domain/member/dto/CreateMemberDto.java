package com.ssafynity_b.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDto {

    @NotBlank(message = "이메일이 공백입니다")
    private String email;

    @NotBlank(message = "비밀번호에 공백이 있습니다")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 숫자, 문자, 특수문자를 최소 1개씩 포함해야합니다")
    private String password;

    @NotBlank(message = "이름이 공백입니다")
    private String name;

    @NotBlank
    private boolean jobSearch; //취준 중이면 true 아니면 false

    private String company; //직장명

    private Boolean companyBlind; //직장명 공개를 원하면 true 아니면 false

    @NotBlank
    private boolean existProfileImage;

}
