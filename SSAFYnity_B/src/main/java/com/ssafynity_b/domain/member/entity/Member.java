package com.ssafynity_b.domain.member.entity;

import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.comment.entity.Comment;
import com.ssafynity_b.domain.common.enums.Campus;
import com.ssafynity_b.domain.member.dto.UpdateMemberDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String password; //패스워드

    @Column(nullable = false)
    private String name; //이름

    @Column(nullable = false)
    private int cohort; //기수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Campus campus; //캠퍼스

    @Column(nullable = false)
    private boolean jobSearch; //취준중이면 true 아니면 false

    @Column(nullable = true)
    private String company; //기재하고싶을 경우에만 값이 존재함, 취준중이거나 회사명을 기재하고싶지 않을경우 -> null

    @NotBlank
    private boolean profileImage; //프로필이미지 사용여부

    @Column(nullable = false)
    private boolean companyBlind; //직장명 공개를 원하면 true 아니면 false

    @Column(nullable = false)
    private String role; //역할

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String password, String name, int cohort, Campus campus, boolean jobSearch, String company, boolean profileImage, boolean companyBlind, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cohort = cohort;
        this.campus = campus;
        this.jobSearch = jobSearch;
        this.company = company;
        this.profileImage = profileImage;
        this.companyBlind = companyBlind;
        this.role = role;
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

    public Member updatdCompany(String company) {
        this.company = company;
        return this;
    }
}
