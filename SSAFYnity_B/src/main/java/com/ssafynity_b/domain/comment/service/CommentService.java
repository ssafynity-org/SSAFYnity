package com.ssafynity_b.domain.comment.service;

import com.ssafynity_b.domain.comment.dto.CommentDto;

public interface CommentService {

    public CommentDto createComment(Long memberId ,Long boardId, String content);

    public CommentDto getComment(Long commentId);

    public CommentDto updateComment(Long commentId, String content);

    public void deleteComment(Long commentId);
}
