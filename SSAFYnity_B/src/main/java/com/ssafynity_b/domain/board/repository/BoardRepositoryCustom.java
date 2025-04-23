package com.ssafynity_b.domain.board.repository;

import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardPageReqDto;
import com.ssafynity_b.domain.board.dto.GetBoardPageResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    //페이지네이션으로 게시물 목록 가져오기
    public GetBoardPageResDto getBoardPage(GetBoardPageReqDto pageReqDto);

}
