package com.ssafynity_b.domain.comment.service;

import com.ssafynity_b.domain.comment.dto.GetCommentDto;

import java.util.List;

public interface CommentService {

    public GetCommentDto createComment(Long memberId , Long boardId, String content);

    public GetCommentDto getComment(Long commentId);

    public List<GetCommentDto> getAllComment();

    public GetCommentDto updateComment(Long commentId, String content);

    public void deleteComment(Long commentId);
}
