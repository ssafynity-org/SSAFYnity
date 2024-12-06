package com.ssafynity_b.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class CreateCommentDto {

    private Long boardId;

    private String content;

}
