package com.ssafinity_b.domain.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDto {

    private String email;
    private String password;
    private String name;
    private String company;

}
