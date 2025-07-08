package com.ssafynity_b.domain.member.dto;

import com.ssafynity_b.domain.common.enums.Campus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "회원 가입을 위한 DTO")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDto {

    @Schema(description = "이메일", example = "SwaggerAdmin@naver.com")
    @NotBlank(message = "이메일이 공백입니다")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank(message = "비밀번호에 공백이 있습니다")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 숫자, 문자, 특수문자를 최소 1개씩 포함해야합니다")
    private String password;

    @Schema(description = "이름", example = "스웨거용 관리자")
    @NotBlank(message = "이름이 공백입니다")
    private String name;

    @Schema(description = "SSAFY입과 기수", example = "10")
    @NotBlank(message = "기수가 공백입니다")
    private int cohort;

    @Schema(description = "캠퍼스")
    @NotBlank(message = "캠퍼스가 공백입니다")
    private Campus campus;

    @Schema(description = "구직 여부", example = "false")
    @NotBlank
    private boolean jobSearch; //취준 중이면 true 아니면 false

    @Schema(description = "직장명", example = "회사 이름을 적어주세요.")
    private String company; //직장명

    private Boolean companyBlind; //직장명 공개를 원하면 true 아니면 false

}
