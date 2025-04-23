package com.ssafynity_b.domain.board.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {

    //제목
    private String title;

    //내용
    private String content;

}
