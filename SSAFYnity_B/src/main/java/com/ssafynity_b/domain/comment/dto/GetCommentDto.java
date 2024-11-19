package com.ssafynity_b.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class GetCommentDto {

    private Long memberId;

    private Long boardId;

    private Long commentId;

    private String content;

}
