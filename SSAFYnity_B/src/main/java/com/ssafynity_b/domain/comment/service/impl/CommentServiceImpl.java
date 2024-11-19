package com.ssafynity_b.domain.comment.service.impl;

import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.board.repository.BoardRepository;
import com.ssafynity_b.domain.comment.dto.CommentDto;
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

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public CommentDto createComment(Long memberId, Long boardId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        Comment comment = new Comment(content);
        comment.setMember(member);
        comment.setBoard(board);
        member.getCommentList().add(comment);
        board.getCommentList().add(comment);
        return CommentDto.builder()
                .memberId(comment.getMember().getMemberId())
                .boardId(comment.getBoard().getBoardId())
                .content(comment.getContent())
                .build();
    }

    @Override
    public CommentDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return CommentDto.builder()
                .memberId(comment.getMember().getMemberId())
                .boardId(comment.getBoard().getBoardId())
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }

    @Transactional
    @Override
    public CommentDto updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.updateContent(content);
        return CommentDto.builder()
                .boardId(comment.getBoard().getBoardId())
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
