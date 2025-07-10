package com.ssafynity_b.domain.board.service;

import com.ssafynity_b.domain.board.dto.*;
import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.board.repository.BoardRepository;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.BoardNotFoundException;
import com.ssafynity_b.global.exception.MemberNotEqualWriterException;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createBoard(CustomUserDetails userDetails, CreateBoardDto createBoardDto) {

        //게시글 저장
        Board board = Board.builder()
                .title(createBoardDto.getTitle())
                .content(createBoardDto.getContent())
                .build();
        board.updateMember(userDetails.getMember());
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    @Override
    public List<GetBoardDto> getAllBoard() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .map(board -> GetBoardDto.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .author(board.getMember().getName())
                        .createdAt(board.getCreatedAt())
                        .views(board.getViews())
                        .likes(board.getLikes())
                        .build())
                .toList();
    }

    @Override
    public GetBoardPageResDto getBoardPage(GetBoardPageReqDto pageReqDto) {
        return boardRepository.getBoardPage(pageReqDto);
    }

    @Override
    public GetBoardDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return GetBoardDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .author(board.getMember().getName())
                .createdAt(board.getCreatedAt())
                .views(board.getViews())
                .likes(board.getLikes())
                .build();
    }

    @Transactional
    @Override
    public Long updateBoard(CustomUserDetails userDetails , UpdateBoardDto updateBoardDto) {

        //현재 접속한 멤버, 게시물, 게시물 글쓴이, 수정할 제목, 수정할 내용
        Member member = userDetails.getMember();
        Board board = boardRepository.findById(updateBoardDto.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Member writer = board.getMember();
        String title = updateBoardDto.getTitle();
        String content = updateBoardDto.getContent();

        System.out.println("현재 접속한 멤버 ID : " + member.getId());
        System.out.println("글쓴이 ID : " + writer.getId());

        //현재 접속한 멤버가 게시물 글쓴이와 다를 경우 수정을 허용하지않음
        if(member.getId()!=writer.getId())
            throw new MemberNotEqualWriterException();

        //게시물 수정
        board.updateTitle(title);
        board.updateContent(content);
        return board.getId();
    }

    @Override
    public void deleteBoard(CustomUserDetails userDetails, Long boardId) {

        //현재 접속한 멤버, 게시물, 게시물 글쓴이
        Member member = userDetails.getMember();
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        Member writer = board.getMember();

        //현재 접속한 멤버가 게시물 글쓴이와 다를 경우 삭제를 허용하지않음
        if(member.getId()!=writer.getId())
            throw new MemberNotEqualWriterException();

        //게시물 삭제
        boardRepository.delete(board);
    }
}
