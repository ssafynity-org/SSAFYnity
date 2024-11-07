package com.ssafinity_b.domain.board.entity;

import com.ssafinity_b.domain.board.dto.CreateBoardDto;
import com.ssafinity_b.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public Board(CreateBoardDto createBoardDto, String filteredHtml) {
        this.title = createBoardDto.getTitle();
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
