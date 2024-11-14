package com.ssafynity_b.domain.message.controller;

import com.ssafynity_b.domain.board.dto.CreateBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.message.dto.CreateMessageDto;
import com.ssafynity_b.domain.message.dto.GetMessageDto;
import com.ssafynity_b.domain.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Tag(name = "Message 컨트롤러")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "쪽지 생성")
    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody CreateMessageDto createMessageDto){
        Long boardId = messageService.createMessage(createMessageDto);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @Operation(summary = "쪽지 전체 조회")
    @GetMapping("/all/{memberId}")
    public ResponseEntity<?> getAllMessage(@PathVariable Long memberId){
        List<GetMessageDto> boardList = messageService.getAllMessage(memberId);
        return new ResponseEntity<List<GetMessageDto>>(boardList, HttpStatus.OK);
    }

    @Operation(summary = "쪽지 조회")
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable Long messageId){
        GetMessageDto board = messageService.getMessage(messageId);
        return new ResponseEntity<GetMessageDto>(board, HttpStatus.OK);
    }

    @Operation(summary = "쪽지 삭제")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId){
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
