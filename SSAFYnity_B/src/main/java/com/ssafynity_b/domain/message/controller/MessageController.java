package com.ssafynity_b.domain.message.controller;

import com.ssafynity_b.domain.board.dto.CreateBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.message.dto.CreateMessageDto;
import com.ssafynity_b.domain.message.dto.GetMessageDto;
import com.ssafynity_b.domain.message.service.MessageService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
@Tag(name = "Message 컨트롤러")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "쪽지 생성")
    @PostMapping
    public ResponseEntity<?> createMessage(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateMessageDto createMessageDto){
        Long boardId = messageService.createMessage(userDetails, createMessageDto);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @Operation(summary = "쪽지 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<?> getAllMessage(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<GetMessageDto> boardList = messageService.getAllMessage(userDetails);
        return new ResponseEntity<List<GetMessageDto>>(boardList, HttpStatus.OK);
    }

    @Operation(summary = "쪽지 단건 조회")
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long messageId){
        GetMessageDto board = messageService.getMessage(userDetails, messageId);
        return new ResponseEntity<GetMessageDto>(board, HttpStatus.OK);
    }

    @Operation(summary = "쪽지 삭제")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long messageId){
        messageService.deleteMessage(userDetails, messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
