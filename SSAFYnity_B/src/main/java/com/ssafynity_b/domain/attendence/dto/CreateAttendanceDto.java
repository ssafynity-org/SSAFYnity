package com.ssafynity_b.domain.attendence.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttendanceDto {

    private int year;
    private int month;

}
