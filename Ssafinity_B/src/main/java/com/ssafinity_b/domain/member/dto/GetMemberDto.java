package com.ssafinity_b.domain.member.dto;

import com.ssafinity_b.domain.member.entity.Member;
import com.ssafinity_b.global.exception.MemberNotFounException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        if(member == null){
            throw new MemberNotFounException(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원을 찾을 수 없습니다."));
        }
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
    }

}
