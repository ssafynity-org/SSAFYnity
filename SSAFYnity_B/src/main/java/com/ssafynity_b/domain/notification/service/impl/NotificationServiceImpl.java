package com.ssafynity_b.domain.notification.service.impl;

import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.domain.message.dto.MessageDto;
import com.ssafynity_b.domain.message.entity.Message;
import com.ssafynity_b.domain.notification.dto.GetNotificationDto;
import com.ssafynity_b.domain.notification.entity.Notification;
import com.ssafynity_b.domain.notification.repository.NotificationRepository;
import com.ssafynity_b.domain.notification.service.NotificationService;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createMessageNotification(Message message) {
        Notification notification = Notification.builder()
                .type(1)
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .build();
        Notification savedNotification = notificationRepository.save(notification);
    }

    @Override
    public List<GetNotificationDto> getAllNotification(Long memberId) {
        Member sender = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<Notification> notificationList = notificationRepository.findAllBySender(sender);
        return notificationList.stream()
                .map(notification -> GetNotificationDto.builder()
                        .notificationId(notification.getNotificationId())
                        .type(notification.getType())
                        .isRead(notification.isRead())
                        .senderId(notification.getSender().getMemberId())
                        .receiverId(notification.getReceiver().getMemberId())
                        .build())
                .toList();
    }

    @Override
    public void save(MessageDto receivedMessage) {
        Member sender = memberRepository.findById(receivedMessage.getSender()).orElseThrow(MemberNotFoundException::new);
        Member receiver = memberRepository.findById(receivedMessage.getReceiver()).orElseThrow(MemberNotFoundException::new);
        Notification notification = Notification.builder()
                .type(1)
                .sender(sender)
                .receiver(receiver)
                .build();
        notificationRepository.save(notification);
    }

}
