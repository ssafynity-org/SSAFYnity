package com.ssafynity_b.domain.board.dto;

import lombok.Data;

@Data
public class GetBoardPageReqDto {

    //현재 페이지 번호
    private int currentPage;

    //현재 페이지의 첫번째 Id
    private Long firstId;

    //현재 페이지의 마지막 Id
    private Long lastId;

    //다음 페이지 번호
    private int nextPage;


}
