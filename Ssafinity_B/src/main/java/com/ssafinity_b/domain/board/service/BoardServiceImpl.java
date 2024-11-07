package com.ssafinity_b.domain.board.service;

import com.ssafinity_b.domain.board.dto.*;
import com.ssafinity_b.domain.board.entity.Board;
import com.ssafinity_b.domain.board.repository.BoardRepository;
import com.ssafinity_b.domain.member.entity.Member;
import com.ssafinity_b.domain.member.repository.MemberRepository;
import com.ssafinity_b.global.exception.BoardNotFoundException;
import com.ssafinity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createBoard(CreateBoardDto createBoardDto) {
        Member member = memberRepository.findById(createBoardDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Board board = new Board(createBoardDto);
        board.updateMember(member);
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getBoardId();
    }

    @Override
    public List<GetBoardDto> getAllBoard() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .map(board -> new GetBoardDto(board.getBoardId(), board.getTitle(), board.getContent()))
                .toList();
    }

    @Override
    public GetBoardDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return new GetBoardDto(board);
    }

    @Transactional
    @Override
    public Long updateBoard(UpdateBoardDto updateBoardDto) {
        Board board = boardRepository.findById(updateBoardDto.getBoardId()).orElseThrow(BoardNotFoundException::new);
        board.updateTitle(updateBoardDto.getTitle());
        board.updateContent(updateBoardDto.getContent());
        return board.getBoardId();
    }

    @Override
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        boardRepository.delete(board);
    }
}
