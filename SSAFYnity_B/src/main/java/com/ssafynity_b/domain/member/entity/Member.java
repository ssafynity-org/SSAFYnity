package com.ssafynity_b.domain.member.entity;

import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.comment.entity.Comment;
import com.ssafynity_b.domain.member.dto.CreateMemberDto;
import com.ssafynity_b.domain.member.dto.UpdateMemberDto;
import com.ssafynity_b.domain.message.entity.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "index_email", columnList = "email"),
        @Index(name = "index_name", columnList = "name")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String company;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default()
    @OneToMany(mappedBy = "sender", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> sendMessageList = new ArrayList<>();

    @Builder.Default()
    @OneToMany(mappedBy = "receiver", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> receiveMessageList = new ArrayList<>();





    public Member(CreateMemberDto createMemberDto){
        this.email = createMemberDto.getEmail();
        this.password = createMemberDto.getPassword();
        this.name = createMemberDto.getName();
        this.company = createMemberDto.getCompany();
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
