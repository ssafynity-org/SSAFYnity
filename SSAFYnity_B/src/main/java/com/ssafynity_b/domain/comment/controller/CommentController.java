package com.ssafynity_b.domain.comment.controller;

import com.ssafynity_b.domain.comment.dto.CommentDto;
import com.ssafynity_b.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "댓글 생성")
    @PostMapping("/{memberId}/{boardId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long memberId, @PathVariable Long boardId, @RequestParam String content){
        CommentDto commentDto = commentService.createComment(memberId, boardId, content);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(Long commentId){
        CommentDto commentDto = commentService.getComment(commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestParam String content){
        CommentDto commentDto = commentService.updateComment(commentId, content);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
