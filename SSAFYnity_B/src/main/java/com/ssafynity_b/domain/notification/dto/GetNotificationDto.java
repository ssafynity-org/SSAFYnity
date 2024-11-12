package com.ssafynity_b.domain.notification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNotificationDto {

    private Long notificationId;
    private int type;
    private boolean isRead;
    private Long senderId;
    private Long receiverId;

}
