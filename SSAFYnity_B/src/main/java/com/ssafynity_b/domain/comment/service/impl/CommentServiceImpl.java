package com.ssafynity_b.domain.comment.service.impl;

import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.board.repository.BoardRepository;
import com.ssafynity_b.domain.comment.dto.CreateCommentDto;
import com.ssafynity_b.domain.comment.dto.GetCommentDto;
import com.ssafynity_b.domain.comment.dto.UpdateCommentDto;
import com.ssafynity_b.domain.comment.entity.Comment;
import com.ssafynity_b.domain.comment.repository.CommentRepository;
import com.ssafynity_b.domain.comment.service.CommentService;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.BoardNotFoundException;
import com.ssafynity_b.global.exception.CommentNotFoundException;
import com.ssafynity_b.global.exception.MemberNotEqualWriterException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import com.ssafynity_b.global.jwt.CustomUserDetails;
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
    public GetCommentDto createComment(CustomUserDetails userDetails, CreateCommentDto createCommentDto) {

        //현재 접속한 멤버, 댓글을 달고자하는 게시물ID, 댓글 내용
        Member member = userDetails.getMember();
        Long boardId = createCommentDto.getBoardId();
        String content = createCommentDto.getContent();

        //게시물에 댓글생성
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
    public GetCommentDto updateComment(CustomUserDetails userDetails, UpdateCommentDto updateCommentDto) {

        //현재 접속한 멤버, 수정할 댓글, 수정할 댓글 작성자, 수정할 내용
        Member member = userDetails.getMember();
        Comment comment = commentRepository.findById(updateCommentDto.getCommentId()).orElseThrow(CommentNotFoundException::new);
        Member writer = comment.getMember();
        String content = updateCommentDto.getContent();

        //현재 접속한 멤버와 댓글 작성자가 일치하는지 확인
        if(member.getId()!=writer.getId())
            throw new MemberNotEqualWriterException();

        //댓글 수정
        comment.updateContent(content);
        return GetCommentDto.builder()
                .boardId(comment.getBoard().getId())
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }

    @Override
    public void deleteComment(CustomUserDetails userDetails, Long commentId) {

        //현재 접속한 멤버, 삭제할 댓글, 삭제할 댓글 작성자
        Member member = userDetails.getMember();
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        Member writer = comment.getMember();

        //현재 접속한 멤버와 댓글 작성자가 일치하는지 확인
        if(member.getId()!=writer.getId())
            throw new MemberNotEqualWriterException();

        //댓글 삭제
        commentRepository.deleteById(commentId);
    }

}
