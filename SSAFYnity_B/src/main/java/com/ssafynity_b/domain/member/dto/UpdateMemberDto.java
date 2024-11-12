package com.ssafynity_b.domain.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDto {

    private Long memberId;
    private String email;
    private String password;
    private String name;

}
