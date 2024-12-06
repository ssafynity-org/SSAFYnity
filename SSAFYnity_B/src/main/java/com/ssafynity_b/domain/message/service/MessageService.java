package com.ssafynity_b.domain.message.service;

import com.ssafynity_b.domain.board.dto.CreateBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.message.dto.CreateMessageDto;
import com.ssafynity_b.domain.message.dto.GetMessageDto;
import com.ssafynity_b.domain.message.dto.MessageDto;
import com.ssafynity_b.global.jwt.CustomUserDetails;

import java.util.List;

public interface MessageService {
    Long createMessage(CustomUserDetails userDetails, CreateMessageDto createMessageDto);

    List<GetMessageDto> getAllMessage(CustomUserDetails userDetails);

    GetMessageDto getMessage(CustomUserDetails userDetails, Long messageId);

    void deleteMessage(CustomUserDetails userDetails, Long messageId);

    void save(MessageDto receivedMessage);
}
