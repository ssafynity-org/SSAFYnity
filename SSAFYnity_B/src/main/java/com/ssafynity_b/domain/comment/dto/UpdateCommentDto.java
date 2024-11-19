package com.ssafynity_b.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentDto {

    private Long commentId;

    private String content;
}
