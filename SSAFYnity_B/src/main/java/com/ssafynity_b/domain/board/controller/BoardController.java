package com.ssafynity_b.domain.board.controller;

import com.ssafynity_b.domain.board.dto.*;
import com.ssafynity_b.domain.board.service.BoardService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Tag(name = "Board 컨트롤러")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 생성")
    @PostMapping
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateBoardDto createBoardDto){
        Long boardId = boardService.createBoard(userDetails, createBoardDto);
        return new ResponseEntity<>(boardId, HttpStatus.OK);
    }

    @Operation(summary = "게시물 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<?> getAllBoard(){
        List<GetBoardDto> boardList = boardService.getAllBoard();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @Operation(summary = "게시물 페이지네이션 조회")
    @GetMapping()
    public ResponseEntity<?> getBoardPage(GetBoardPageReqDto pageReqDto){
        Map<String, Object> responseBody = new HashMap<>();
        GetBoardPageResDto response = boardService.getBoardPage(pageReqDto);
        responseBody.put("data", response);
        responseBody.put("success", "success");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "게시물 단일 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId){
        GetBoardDto board = boardService.getBoard(boardId);
        return new ResponseEntity<GetBoardDto>(board, HttpStatus.OK);
    }

    @Operation(summary = "게시물 수정")
    @PutMapping
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateBoardDto updateBoardDto){
        Long boardId = boardService.updateBoard(userDetails, updateBoardDto);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long boardId){
        boardService.deleteBoard(userDetails, boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
