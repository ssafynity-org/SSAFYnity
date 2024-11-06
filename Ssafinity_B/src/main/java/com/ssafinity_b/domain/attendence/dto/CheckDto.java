package com.ssafinity_b.domain.attendence.dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

    private Long memberId;
    private String date;
    private String time;

}
