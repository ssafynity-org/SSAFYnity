package com.ssafynity_b.domain.message.dto;

import com.ssafynity_b.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageDto {

    //보낼 메세지 내용
    private String content;

    //수신자Id
    private Long receiverId;

}
