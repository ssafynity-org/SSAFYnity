package com.ssafynity_b.domain.message.service;

import com.ssafynity_b.domain.board.dto.CreateBoardDto;
import com.ssafynity_b.domain.board.dto.GetBoardDto;
import com.ssafynity_b.domain.message.dto.CreateMessageDto;
import com.ssafynity_b.domain.message.dto.GetMessageDto;
import com.ssafynity_b.domain.message.dto.MessageDto;

import java.util.List;

public interface MessageService {
    Long createMessage(CreateMessageDto createMessageDto);

    List<GetMessageDto> getAllMessage(Long memberId);

    GetMessageDto getMessage(Long messageId);

    void deleteMessage(Long messageId);

    void save(MessageDto receivedMessage);
}
