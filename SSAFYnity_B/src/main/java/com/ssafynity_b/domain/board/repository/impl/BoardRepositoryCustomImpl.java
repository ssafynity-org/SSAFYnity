package com.ssafynity_b.domain.board.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardPageReqDto;
import com.ssafynity_b.domain.board.dto.GetBoardPageResDto;
import com.ssafynity_b.domain.board.entity.QBoard;
import com.ssafynity_b.domain.board.repository.BoardRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public GetBoardPageResDto getBoardPage(GetBoardPageReqDto pageReqDto) {
        QBoard board = QBoard.board;

        //현재 pageNumber의 일의자리에 따라 탐색해야할 다음페이지의 rows가 달라짐
        int searchRows = 0;
        switch (pageReqDto.getPageNumber()%10) {

            case 1 :
                searchRows = 181;
                break;

            case 2 :
                searchRows = 161;
                break;

            case 3 :
                searchRows = 141;
                break;

            case 4 :
                searchRows = 121;
                break;

            case 5 :
                searchRows = 101;
                break;

            case 6 :
                searchRows = 81;
                break;

            case 7 :
                searchRows = 61;
                break;

            case 8 :
                searchRows = 41;
                break;

            case 9 :
                searchRows = 21;
                break;

            case 0 :
                searchRows = 1;
                break;

        }

        //OFFSET 범위 지정
        int offsetNum = (pageReqDto.getPageNumber()-1)*20;
        System.out.println("offsetNum : " + offsetNum);
        //콘텐츠 조회
        List<GetBoardDto> content = queryFactory
                .select(Projections.constructor(GetBoardDto.class,
                        board.id,
                        board.title,
                        board.content,
                        board.createdAt,
                        board.views,
                        board.likes,
                        board.member.name.as("author")))
                .from(board)
                .limit(20)
                .offset(offsetNum)
                .orderBy(board.id.desc())
                .fetch();

        System.out.println("content : " + content.size());

        //전체 개수 조회
        List<Long> nextRows = queryFactory
                .select(board.id)
                .from(board)
                .where(board.id.lt(content.get(content.size()-1).getBoardId()))
                .limit(searchRows)
                .fetch();

        int startPage = ((pageReqDto.getPageNumber() - 1) / 10) * 10 + 1;
        int currentPage = pageReqDto.getPageNumber();
        int endPage = currentPage + (nextRows.size() / 20);

        //표시된 페이지 중 맨끝 페이지의 마지막Id('>' 버튼 클릭시 활용함)
        Long rangeLastId = nextRows.isEmpty() ? null : nextRows.get(nextRows.size() - 1);

        //찾아야될 행 수보다 적게 찾았다면 '>'버튼 비활성화, '>>'버튼 비활성화
        //찾아야될 행 수 만큼 찾았다면 '>'버튼 활성화, '>>'버튼 활성화
        boolean nextButton = false;
        boolean lastButton = false;
        if(nextRows.size()==searchRows){
            nextButton = true;
            lastButton = true;
        }

        System.out.println("nextButton : " + nextButton);
        System.out.println("lastButton : " + lastButton);

        return new GetBoardPageResDto<>(content, startPage, endPage, nextButton, lastButton, rangeLastId);
    }
}
