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
import com.ssafynity_b.global.exception.MemberNotEqualReceiverException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import com.ssafynity_b.global.exception.MessageNotFoundException;
import com.ssafynity_b.global.jwt.CustomUserDetails;
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
    public Long createMessage(CustomUserDetails userDetails, CreateMessageDto createMessageDto) {
        //현재 접속한 멤버(발신자), 메세지를 받을 멤버(수신자), 메세지 내용
        Member sender = userDetails.getMember();
        Member receiver = memberRepository.findById(createMessageDto.getReceiverId()).orElseThrow(MemberNotFoundException::new);
        String content = createMessageDto.getContent();

        //메세지 전송
        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .receiver(receiver)
                .build();
        Message savedMessage = messageRepository.save(message);
        notificationService.createMessageNotification(message);
        return savedMessage.getId();
    }

    @Override
    public List<GetMessageDto> getAllMessage(CustomUserDetails userDetails) {

        //현재 접속한 멤버, 해당 멤버가 수신자로 설정되어있는 전체 메세지 리스트
        Member member = userDetails.getMember();
        List<Message> messageList = messageRepository.findByReceiver(member);


        return messageList.stream()
                .map(message -> GetMessageDto.builder()
                        .messageId(message.getId())
                        .content(message.getContent())
                        .isRead(message.isRead())
                        .senderId(message.getSender().getId())
                        .receiverId(message.getReceiver().getId())
                        .build())
                .toList();
    }

    @Override
    public GetMessageDto getMessage(CustomUserDetails userDetails, Long messageId) {

        //현재 접속한 멤버, 조회할 메세지, 메세지 수신자
        Member member = userDetails.getMember();
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        Member receiver = message.getReceiver();

        //현재 접속한 멤버과 메세지 수신자가 다르다면 조회 불가
        if(member.getId() != receiver.getId())
            throw new MemberNotEqualReceiverException();

        return GetMessageDto.builder()
                .messageId(message.getId())
                .content(message.getContent())
                .isRead(message.isRead())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .build();
    }

    @Override
    public void deleteMessage(CustomUserDetails userDetails, Long messageId) {

        //현재 접속한 멤버, 삭제할 메세지, 메세지 수신자
        Member member = userDetails.getMember();
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        Member receiver = message.getReceiver();

        //현재 접속한 멤버가 메세지 수신자가 아니라면 삭제 불가
        if(member.getId() != receiver.getId())
            throw new MemberNotEqualReceiverException();

        //메세지 삭제
        messageRepository.deleteById(messageId);
    }

    @Override
    public void save(MessageDto receivedMessage) {
        Member sender = memberRepository.findById(receivedMessage.getSender()).orElseThrow(MemberNotFoundException::new);
        Member receiver = memberRepository.findById(receivedMessage.getReceiver()).orElseThrow(MemberNotFoundException::new);
        Message message = Message.builder()
                .content(receivedMessage.getMessage())
                .sender(sender)
                .receiver(receiver)
                .build();
        messageRepository.save(message);
    }
}
