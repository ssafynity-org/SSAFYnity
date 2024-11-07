package com.ssafinity_b.domain.board.dto;

import com.ssafinity_b.domain.board.entity.Board;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardDto {

    private Long boardId;
    private String title;
    private String content;

    public GetBoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
