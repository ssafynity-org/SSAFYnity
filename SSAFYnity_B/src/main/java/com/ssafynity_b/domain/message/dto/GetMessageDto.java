package com.ssafynity_b.domain.message.dto;

import com.ssafynity_b.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageDto {

    private Long messageId;
    private String message;
    private Long senderId;
    private Long receiverId;
    private boolean isRead;

}
