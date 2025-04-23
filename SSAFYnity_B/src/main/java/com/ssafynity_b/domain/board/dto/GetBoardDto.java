package com.ssafynity_b.domain.board.dto;

import com.ssafynity_b.domain.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetBoardDto {

    //PK값
    private Long boardId;

    //제목
    private String title;

    //내용
    private String content;

    //작성일
    private LocalDateTime createdAt;

    //조회수
    private int views;

    //좋아요
    private int likes;

    //작성자
    private String author;

    @Builder
    public GetBoardDto(Long boardId, String title, String content, LocalDateTime createdAt, int views, int likes, String author) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.likes = likes;
        this.author = author;
    }
}
