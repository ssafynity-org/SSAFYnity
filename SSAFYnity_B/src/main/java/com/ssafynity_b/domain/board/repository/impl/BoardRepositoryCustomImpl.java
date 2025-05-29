package com.ssafynity_b.domain.board.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardPageReqDto;
import com.ssafynity_b.domain.board.dto.GetBoardPageResDto;
import com.ssafynity_b.domain.board.entity.QBoard;
import com.ssafynity_b.domain.board.repository.BoardRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public GetBoardPageResDto getBoardPage(GetBoardPageReqDto pageReqDto) {
        QBoard board = QBoard.board;

        System.out.println("응답 시작");
        System.out.println("라스트 버튼 눌렀는지 : " + pageReqDto.isLastButton());

        if(pageReqDto.isLastButton()){
            int total = queryFactory
                    .select(board.count())
                    .from(board)
                    .where(board.id.lt(pageReqDto.getLastId()))
                    .fetchOne().intValue();

            pageReqDto.setNextPage(pageReqDto.getCurrentPage()+(total/20));
        }

        //현재 pageNumber의 일의자리에 따라 탐색해야할 다음페이지의 rows가 달라짐
        int searchNext = 0;
        int searchPrev = 0;

        switch (pageReqDto.getNextPage()%10) {

            case 1 :
                searchNext = 181;
                searchPrev = 1;
                break;

            case 2 :
                searchNext = 161;
                searchPrev = 21;
                break;

            case 3 :
                searchNext = 141;
                searchPrev = 41;
                break;

            case 4 :
                searchNext = 121;
                searchPrev = 61;
                break;

            case 5 :
                searchNext = 101;
                searchPrev = 81;
                break;

            case 6 :
                searchNext = 81;
                searchPrev = 101;
                break;

            case 7 :
                searchNext = 61;
                searchPrev = 121;
                break;

            case 8 :
                searchNext = 41;
                searchPrev = 141;
                break;

            case 9 :
                searchNext = 21;
                searchPrev = 161;
                break;

            case 0 :
                searchNext = 1;
                searchPrev = 181;
                break;

        }
        //이동할 페이지와 현재 페이지가 얼마나 차이가 나는지 확인
        int pageDiff = pageReqDto.getNextPage() - pageReqDto.getCurrentPage();
        System.out.println("pageReqDto.getNextPage() :" + pageReqDto.getNextPage());
        System.out.println("pageReqDto.getCurrentPage() : " + pageReqDto.getCurrentPage());
        System.out.println("pageDiff : " + pageDiff);
        int semiOffSet = 0;
        if(Math.abs(pageDiff)>0){
            semiOffSet = 20*(Math.abs(pageDiff)-1);
        }

        System.out.println("지금들어온 firstId : " + pageReqDto.getFirstId());
        System.out.println("지금 차이난 pageDiff : " + pageDiff);
        System.out.println("semiOffSet : " + semiOffSet);

        // 1단계: 커버링 인덱스를 이용한 ID 목록 조회
        List<Long> boardIds = queryFactory
                .select(board.id)
                .from(board)
                .where(
                        pageDiff > 0
                                ? (pageReqDto.getLastId() != null ? board.id.lt(pageReqDto.getLastId()) : null)
                                : (pageReqDto.getFirstId() != null ? board.id.gt(pageReqDto.getFirstId()) : null)
                )
                .orderBy(pageDiff >= 0 ? board.id.desc() : board.id.asc())
                .limit(20)
                .offset(semiOffSet)
                .fetch();

// 2단계: 실제 데이터 조회
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
                .where(board.id.in(boardIds))
                .orderBy(pageDiff >= 0 ? board.id.desc() : board.id.asc()) // 정렬 유지
                .fetch();

        //콘텐츠 조회
//        List<GetBoardDto> content = queryFactory
//                .select(Projections.constructor(GetBoardDto.class,
//                        board.id,
//                        board.title,
//                        board.content,
//                        board.createdAt,
//                        board.views,
//                        board.likes,
//                        board.member.name.as("author")))
//                .from(board)
//                .where(
//                        pageDiff > 0
//                                ? (pageReqDto.getLastId() != null ? board.id.lt(pageReqDto.getLastId()) : null)
//                                : (pageReqDto.getFirstId() != null ? board.id.gt(pageReqDto.getFirstId()) : null)
//                )
//                .limit(20)
//                .offset(semiOffSet)
//                .orderBy(pageDiff >= 0 ? board.id.desc() : board.id.asc())
//                .fetch();

        System.out.println("content : " + content.size());

        //현재 페이지 기준 첫번째 Id
        Long firstId = content.get(0).getBoardId();

        //현재 페이지 기준 마지막 Id
        Long lastId = content.get(content.size()-1).getBoardId();

        //ASC, DESC 정렬 기준을 사용하기때문에 때에 맞게 유동적으로 조정하기 위함
        if(firstId<lastId){
            Long temp = null;
            temp = firstId;
            firstId = lastId;
            lastId = temp;
        }

        //앞 row 개수 조회
        List<Long> nextRows = queryFactory
                .select(board.id)
                .from(board)
                .where(board.id.lt(lastId))
                .limit(searchNext)
                .fetch();

        //뒤 row 개수 조회
        List<Long> prevRows = queryFactory
                .select(board.id)
                .from(board)
                .where(board.id.gt(firstId))
                .limit(searchNext)
                .fetch();

        //이동할 페이지 기준으로 StartPage와 EndPage 계산
        int startPage = ((pageReqDto.getNextPage() - 1) / 10) * 10 + 1;
        int currentPage = pageReqDto.getNextPage();
        int endPage = currentPage + (nextRows.size() / 20);

        //표시된 페이지 중 맨첫 페이지의 첫번째Id('<' 버튼 클릭시 활용함)
        Long rangeFirstId = prevRows.isEmpty() ? null : prevRows.get(prevRows.size() - 1);

        //표시된 페이지 중 맨끝 페이지의 마지막Id('>' 버튼 클릭시 활용함)
        Long rangeLastId = nextRows.isEmpty() ? null : nextRows.get(nextRows.size() - 1);

        //찾아야될 행 수보다 적게 찾았다면 '>'버튼 비활성화, '>>'버튼 비활성화
        //찾아야될 행 수 만큼 찾았다면 '>'버튼 활성화, '>>'버튼 활성화
        boolean nextButton = false;
        boolean lastButton = false;
        if(nextRows.size()==searchNext){
            nextButton = true;
            lastButton = true;
        }

        System.out.println("nextButton : " + nextButton);
        System.out.println("lastButton : " + lastButton);

        if (pageDiff < 0) {
            Collections.reverse(content);
        }
        System.out.println("이제 랜더링될 startPage : " + startPage);
        System.out.println("이제 랜더링될 currentPage : " + currentPage);
        System.out.println("이제 랜더링될 endPage : " + endPage);
        System.out.println("이제 랜더링될 firstId : " + firstId);
        System.out.println("이제 랜더링될 lastId : " + lastId);

        return new GetBoardPageResDto<>(content, startPage, currentPage, firstId, lastId, endPage, nextButton, lastButton, rangeFirstId, rangeLastId);
    }
}
