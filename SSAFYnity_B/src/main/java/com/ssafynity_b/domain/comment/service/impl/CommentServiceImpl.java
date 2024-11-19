package com.ssafynity_b.domain.comment.service.impl;

import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.board.repository.BoardRepository;
import com.ssafynity_b.domain.comment.dto.GetCommentDto;
import com.ssafynity_b.domain.comment.entity.Comment;
import com.ssafynity_b.domain.comment.repository.CommentRepository;
import com.ssafynity_b.domain.comment.service.CommentService;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.BoardNotFoundException;
import com.ssafynity_b.global.exception.CommentNotFoundException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public GetCommentDto createComment(Long memberId, Long boardId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        Comment comment = new Comment(content);
        comment.setMember(member);
        comment.setBoard(board);
        member.getCommentList().add(comment);
        board.getCommentList().add(comment);
        return GetCommentDto.builder()
                .memberId(comment.getMember().getId())
                .boardId(comment.getBoard().getId())
                .content(comment.getContent())
                .build();
    }

    @Override
    public GetCommentDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return GetCommentDto.builder()
                .memberId(comment.getMember().getId())
                .boardId(comment.getBoard().getId())
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }

    @Override
    public List<GetCommentDto> getAllComment() {
        List<Comment> commentList = commentRepository.findAll();
        List<GetCommentDto> commentDtoList = commentList.stream().map(comment -> GetCommentDto.builder()
                        .memberId(comment.getMember().getId())
                        .boardId(comment.getBoard().getId())
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .build())
                .toList();
        return commentDtoList;
    }

    @Transactional
    @Override
    public GetCommentDto updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.updateContent(content);
        return GetCommentDto.builder()
                .boardId(comment.getBoard().getId())
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
