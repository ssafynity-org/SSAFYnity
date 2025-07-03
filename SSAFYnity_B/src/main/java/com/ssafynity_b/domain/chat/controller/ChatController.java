package com.ssafynity_b.domain.chat.controller;

import com.ssafynity_b.domain.chat.entity.ChatMessage;
import com.ssafynity_b.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository messageRepository;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage send(@DestinationVariable Long roomId, ChatMessage message) {
        message.setRoomId(roomId);
        messageRepository.save(message);
        return message;
    }

}
