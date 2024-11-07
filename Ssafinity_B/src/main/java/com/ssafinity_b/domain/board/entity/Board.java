package com.ssafinity_b.domain.board.entity;

import com.ssafinity_b.domain.board.dto.CreateBoardDto;
import com.ssafinity_b.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long boardId;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Board(CreateBoardDto createBoardDto) {
        this.title = createBoardDto.getTitle();
        this.content = createBoardDto.getContent();
    }

    public void updateMember(Member member){
        this.member = member;
    }

}
