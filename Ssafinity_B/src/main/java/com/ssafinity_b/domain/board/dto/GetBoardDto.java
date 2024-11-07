package com.ssafinity_b.domain.board.dto;

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

}
