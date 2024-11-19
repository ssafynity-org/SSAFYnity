package com.ssafynity_b.domain.member.dto;

import com.ssafynity_b.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberDto {

    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String company;

    public GetMemberDto(Member member){
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
        this.company = member.getCompany();
    }

}
