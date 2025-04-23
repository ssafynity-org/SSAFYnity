package com.ssafynity_b.domain.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetBoardPageResDto<T> {

    //조회 내용
    private List<T> content;

    //시작 페이지
    private int startPage;

    //끝 페이지
    private int endPage;

    //'>' 버튼 활성화 여부
    private boolean nextButton;

    //'>>' 버튼 활성화 여부
    private boolean lastButton;

    //표시된 페이지 중 맨끝 페이지의 마지막Id('>' 버튼 클릭시 활용함)
    private Long rangeLastId;

    public GetBoardPageResDto(List<T> content, int startPage, int endPage, boolean nextButton, boolean lastButton, Long rangeLastId) {
        this.content = content;
        this.startPage = startPage;
        this.endPage = endPage;
        this.nextButton = nextButton;
        this.lastButton = lastButton;
        this.rangeLastId = rangeLastId;
    }
}
