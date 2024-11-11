package com.ssafynity_b.domain.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardDto {

    private Long boardId;
    private String title;
    private String content;

}
