package com.ssafinity_b.domain.board.service;

import com.ssafinity_b.domain.board.dto.CreateBoardDto;
import com.ssafinity_b.domain.board.dto.GetBoardDto;
import com.ssafinity_b.domain.board.dto.UpdateBoardDto;

import java.util.List;

public interface BoardService {

    Long createBoard(CreateBoardDto createBoardDto);

    List<GetBoardDto> getAllBoard();

    GetBoardDto getBoard(Long boardId);

    Long updateBoard(UpdateBoardDto updateBoardDto);

    void deleteBoard(Long boardId);
}
