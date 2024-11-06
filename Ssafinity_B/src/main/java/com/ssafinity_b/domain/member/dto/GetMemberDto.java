package com.ssafinity_b.domain.member.dto;

import com.ssafinity_b.domain.member.entity.Member;
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

    public GetMemberDto(Member member){
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
    }

}
