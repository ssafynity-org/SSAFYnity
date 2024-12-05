package com.ssafynity_b.domain.attendence.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordDto {

    //수정할 년도
    private int year;
    //수정할 달
    private int month;
    //수정할 날
    private int day;
    //체크인 시간
    private String checkInTime;
    //체크아웃 시간
    private String checkOutTime;
    //상태
    private String status;

}
