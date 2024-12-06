package com.ssafynity_b.domain.comment.service;

import com.ssafynity_b.domain.comment.dto.CreateCommentDto;
import com.ssafynity_b.domain.comment.dto.GetCommentDto;
import com.ssafynity_b.domain.comment.dto.UpdateCommentDto;
import com.ssafynity_b.global.jwt.CustomUserDetails;

import java.util.List;

public interface CommentService {

    public GetCommentDto createComment(CustomUserDetails userDetails, CreateCommentDto createCommentDto);

    public GetCommentDto getComment(Long commentId);

    public List<GetCommentDto> getAllComment();

    public GetCommentDto updateComment(CustomUserDetails userDetails, UpdateCommentDto updateCommentDto);

    public void deleteComment(CustomUserDetails userDetails, Long commentId);
}
