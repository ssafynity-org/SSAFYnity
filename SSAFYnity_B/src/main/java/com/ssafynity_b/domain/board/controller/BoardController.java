package com.ssafynity_b.domain.board.controller;

import com.ssafynity_b.domain.board.dto.*;
import com.ssafynity_b.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Tag(name = "Board 컨트롤러")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 생성")
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody CreateBoardDto createBoardDto){
        Long boardId = boardService.createBoard(createBoardDto);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @Operation(summary = "게시물 전체 조회")
    @GetMapping
    public ResponseEntity<?> getAllBoard(){
        List<GetBoardDto> boardList = boardService.getAllBoard();
        return new ResponseEntity<List<GetBoardDto>>(boardList, HttpStatus.OK);
    }

    @Operation(summary = "게시물 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId){
        GetBoardDto board = boardService.getBoard(boardId);
        return new ResponseEntity<GetBoardDto>(board, HttpStatus.OK);
    }

    @Operation(summary = "게시물 수정")
    @PutMapping
    public ResponseEntity<?> updateBoard(@RequestBody UpdateBoardDto updateBoardDto){
        Long boardId = boardService.updateBoard(updateBoardDto);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
