package com.ssafynity_b.domain.board.service;

import com.ssafynity_b.domain.board.dto.*;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    //게시물 생성
    Long createBoard(CustomUserDetails userDetails, CreateBoardDto createBoardDto);

    //게시물 전체 조회
    List<GetBoardDto> getAllBoard();

    //게시물 페이지네이션 조회
    GetBoardPageResDto getBoardPage(GetBoardPageReqDto pageReqDto);

    //게시물 단건 조회
    GetBoardDto getBoard(Long boardId);

    //게시물 수정
    Long updateBoard(CustomUserDetails userDetails, UpdateBoardDto updateBoardDto);

    //게시물 삭제
    void deleteBoard(CustomUserDetails userDetails, Long boardId);
}
