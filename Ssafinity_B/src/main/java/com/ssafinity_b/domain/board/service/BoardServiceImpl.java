package com.ssafinity_b.domain.board.service;

import com.ssafinity_b.domain.board.dto.CreateBoardDto;
import com.ssafinity_b.domain.board.dto.GetBoardDto;
import com.ssafinity_b.domain.board.dto.UpdateBoardDto;
import com.ssafinity_b.domain.board.entity.Board;
import com.ssafinity_b.domain.board.repository.BoardRepository;
import com.ssafinity_b.domain.member.entity.Member;
import com.ssafinity_b.domain.member.repository.MemberRepository;
import com.ssafinity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return 0;
    }

    @Override
    public List<GetBoardDto> getAllBoard() {
        return List.of();
    }

    @Override
    public GetBoardDto getBoard(Long boardId) {
        return null;
    }

    @Override
    public Long updateBoard(UpdateBoardDto updateBoardDto) {
        return 0;
    }

    @Override
    public void deleteBoard(Long boardId) {

    }
}
