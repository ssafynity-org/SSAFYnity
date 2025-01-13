package com.ssafynity_b.domain.member.dto;

import com.ssafynity_b.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class GetLoginDto {

    private Long memberId;
    private String name;
    private String company;
    private String profileImage;

    public GetLoginDto(Member member, String profileImage) {
        this.memberId = member.getId();
        this.name = member.getName();
        this.company = member.getCompany();
        this.profileImage = profileImage;
    }
}
