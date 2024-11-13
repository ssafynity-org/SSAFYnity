package com.ssafynity_b.domain.notification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    // 0 : 에러
    // 1 : 쪽지
    private int type;
    private Long sender;
    private Long receiver;
    private String message;
    private String time;
    private String errorMessage;

}
