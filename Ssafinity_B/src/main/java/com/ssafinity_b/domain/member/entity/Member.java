package com.ssafinity_b.domain.member.entity;

import com.ssafinity_b.domain.board.entity.Board;
import com.ssafinity_b.domain.member.dto.UpdateMemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Board> boardList;




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

    public Member updateEmail(String email) {
        this.email = email;
        return this;
    }

    public Member updatePassword(String password) {
        this.password = password;
        return this;
    }

    public Member updateName(String name) {
        this.name = name;
        return this;
    }
}
