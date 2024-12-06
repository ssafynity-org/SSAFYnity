package com.ssafynity_b.domain.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {

    //게시글 제목
    private String title;
    //게시글 내용
    private String content;

}
