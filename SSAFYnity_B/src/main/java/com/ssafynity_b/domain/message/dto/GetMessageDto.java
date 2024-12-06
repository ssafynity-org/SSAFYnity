package com.ssafynity_b.domain.message.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageDto {

    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;
    private boolean isRead;

}
