package com.ssafinity_b.domain.attendence.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

    private Long memberId;
    private String date;

}
