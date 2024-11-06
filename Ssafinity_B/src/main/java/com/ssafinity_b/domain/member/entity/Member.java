package com.ssafinity_b.domain.member.entity;

import com.ssafinity_b.domain.member.dto.UpdateMemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String name;

    public static Member of(String email, String password, String name){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }

    public void updateMember(UpdateMemberDto memberDto){
        this.email = memberDto.getEmail();
        this.password = memberDto.getPassword();
        this.name = memberDto.getName();
    }

}
