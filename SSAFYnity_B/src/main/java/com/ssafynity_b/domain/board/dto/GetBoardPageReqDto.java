package com.ssafynity_b.domain.board.dto;

import lombok.Data;

@Data
public class GetBoardPageReqDto {

    //현재 페이지 번호
    private int pageNumber;

    //현재 페이지의 마지막 Id
    private long lastId;

}
