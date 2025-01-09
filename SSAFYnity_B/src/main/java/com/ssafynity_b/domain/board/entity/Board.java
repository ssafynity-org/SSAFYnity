package com.ssafynity_b.domain.board.entity;

import com.ssafynity_b.domain.board.dto.CreateBoardDto;
import com.ssafynity_b.domain.comment.entity.Comment;
import com.ssafynity_b.domain.member.entity.Member;
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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String title;

    @Basic(fetch = FetchType.LAZY) //단일 필드를 지연로딩으로 설정하기위한 어노테이션
    @Lob //대규모 문자열이기때문에 Lob어노테이션 사용
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Comment> commentList = new ArrayList<>();

    public Board(CreateBoardDto createBoardDto) {
        this.title = createBoardDto.getTitle();
        this.content = createBoardDto.getContent();
    }

    public Board(String title, String filteredHtml) {
        this.title = title;
        this.content = filteredHtml;
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
