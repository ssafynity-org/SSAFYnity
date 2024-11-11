package com.ssafynity_b.domain.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {

    private Long memberId;
    private String title;
    private String content;

}
