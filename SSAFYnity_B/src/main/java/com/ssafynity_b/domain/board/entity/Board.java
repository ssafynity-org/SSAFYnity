package com.ssafynity_b.domain.board.entity;

import com.ssafynity_b.domain.board.dto.CreateBoardDto;
import com.ssafynity_b.domain.comment.entity.Comment;
import com.ssafynity_b.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //제목
    private String title;

    //내용
    @Basic(fetch = FetchType.LAZY) //단일 필드를 지연로딩으로 설정하기위한 어노테이션
    @Lob //대규모 문자열이기때문에 Lob어노테이션 사용
    private String content;

    //작성일
    private LocalDateTime createdAt;

    //조회수
    private int views;

    //좋아요
    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Comment> commentList = new ArrayList<>();

//    @Builder
//    public Board(String title, String content) {
//        this.title = title;
//        this.content = content;
//        this.createdAt = LocalDateTime.now(); // 생성 시 자동으로 현재 시간 설정
//        this.views = 0;
//        this.likes = 0;
//    }

    @Builder
    public Board(String title, String content, LocalDateTime createdAt, int views, int likes) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.likes = likes;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateMember(Member member){
        this.member = member;
    }

}
