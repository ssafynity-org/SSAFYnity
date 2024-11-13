package com.ssafynity_b.domain.message.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long sender;
    private Long receiver;
    private String message;
    private String time;

}
