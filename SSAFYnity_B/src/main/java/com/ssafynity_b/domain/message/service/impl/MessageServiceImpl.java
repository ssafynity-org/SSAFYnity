package com.ssafynity_b.domain.message.service.impl;

import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.domain.message.dto.CreateMessageDto;
import com.ssafynity_b.domain.message.dto.GetMessageDto;
import com.ssafynity_b.domain.message.dto.MessageDto;
import com.ssafynity_b.domain.message.entity.Message;
import com.ssafynity_b.domain.message.repository.MessageRepository;
import com.ssafynity_b.domain.message.service.MessageService;
import com.ssafynity_b.domain.notification.service.NotificationService;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import com.ssafynity_b.global.exception.MessageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final NotificationService notificationService;

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long createMessage(CreateMessageDto createMessageDto) {
        Member sender = memberRepository.findById(createMessageDto.getSenderId()).orElseThrow(MemberNotFoundException::new);
        Member receiver = memberRepository.findById(createMessageDto.getReceiverId()).orElseThrow(MemberNotFoundException::new);
        Message message = Message.builder()
                .message(createMessageDto.getMessage())
                .sender(sender)
                .receiver(receiver)
                .build();
        Message savedMessage = messageRepository.save(message);
        notificationService.createMessageNotification(message);
        return savedMessage.getMessageId();
    }

    @Override
    public List<GetMessageDto> getAllMessage(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<Message> messageList = messageRepository.findByReceiver(member);
        return messageList.stream()
                .map(message -> GetMessageDto.builder()
                        .messageId(message.getMessageId())
                        .message(message.getMessage())
                        .isRead(message.isRead())
                        .senderId(message.getSender().getMemberId())
                        .receiverId(message.getReceiver().getMemberId())
                        .build())
                .toList();
    }

    @Override
    public GetMessageDto getMessage(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        return GetMessageDto.builder()
                .messageId(message.getMessageId())
                .message(message.getMessage())
                .isRead(message.isRead())
                .senderId(message.getSender().getMemberId())
                .receiverId(message.getReceiver().getMemberId())
                .build();
    }

    @Override
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    @Override
    public void save(MessageDto receivedMessage) {
        Member sender = memberRepository.findById(receivedMessage.getSender()).orElseThrow(MemberNotFoundException::new);
        Member receiver = memberRepository.findById(receivedMessage.getReceiver()).orElseThrow(MemberNotFoundException::new);
        Message message = Message.builder()
                .message(receivedMessage.getMessage())
                .sender(sender)
                .receiver(receiver)
                .build();
        messageRepository.save(message);
    }
}
