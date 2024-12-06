package com.ssafynity_b.domain.comment.controller;

import com.ssafynity_b.domain.comment.dto.GetCommentDto;
import com.ssafynity_b.domain.comment.dto.CreateCommentDto;
import com.ssafynity_b.domain.comment.dto.UpdateCommentDto;
import com.ssafynity_b.domain.comment.service.CommentService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "댓글 생성")
    @PostMapping
    public ResponseEntity<GetCommentDto> createComment(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateCommentDto createCommentDto){
        GetCommentDto getCommentDto = commentService.createComment(userDetails, createCommentDto);
        return new ResponseEntity<>(getCommentDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentDto> getComment(@PathVariable Long commentId){
        GetCommentDto getCommentDto = commentService.getComment(commentId);
        return new ResponseEntity<>(getCommentDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 전체 조회")
    @GetMapping
    public ResponseEntity<List<GetCommentDto>> getCommentAll(){
        List<GetCommentDto> commentDtoList = commentService.getAllComment();
        return new ResponseEntity<>(commentDtoList,HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping
    public ResponseEntity<GetCommentDto> updateComment(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateCommentDto updateCommentDto){
        GetCommentDto getCommentDto = commentService.updateComment(userDetails, updateCommentDto);
        return new ResponseEntity<>(getCommentDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long commentId){
        commentService.deleteComment(userDetails, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
