package com.ssafynity_b.domain.board.service;

import com.ssafynity_b.domain.board.dto.*;
import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.board.repository.BoardRepository;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.BoardNotFoundException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
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

        // WYSIWYG에 적합한 Safelist 설정
        Safelist customWhitelist = Safelist.relaxed()
                .addTags("h1", "h2", "h3", "h4", "h5", "h6", "p", "b", "i", "u", "strong", "em", "blockquote", "ul", "ol", "li", "a", "img", "div", "span", "table", "tr", "td", "th", "hr")
                .addAttributes("a", "href", "title")
                .addAttributes("img", "src", "alt", "title")
                .addAttributes("table", "border", "cellpadding", "cellspacing")
                .addAttributes("tr", "align")
                .addAttributes("td", "colspan", "rowspan", "align");

        // Jsoup을 사용해 필터링
        String filteredHtml = Jsoup.clean(createBoardDto.getContent(), customWhitelist);

        Board board = new Board(createBoardDto, filteredHtml);
        board.updateMember(member);
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    @Override
    public List<GetBoardDto> getAllBoard() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .map(board -> new GetBoardDto(board.getId(), board.getTitle(), board.getContent()))
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
        return board.getId();
    }

    @Override
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        boardRepository.delete(board);
    }
}
