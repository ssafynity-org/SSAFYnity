package com.ssafinity_b.domain.attendence.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordDto {

    private String id;
    private int day;
    private String checkInTime;
    private String checkOutTime;
    private String status;

}
