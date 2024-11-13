package com.ssafynity_b.domain.notification.service;

import com.ssafynity_b.domain.message.dto.MessageDto;
import com.ssafynity_b.domain.message.entity.Message;
import com.ssafynity_b.domain.notification.dto.GetNotificationDto;

import java.util.List;

public interface NotificationService {

    void createMessageNotification(Message message);

    List<GetNotificationDto> getAllNotification(Long memberId);

    void save(MessageDto receivedMessage);
}
