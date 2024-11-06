package com.ssafinity_b.domain.attendence.entity;

import com.ssafinity_b.domain.attendence.dto.UpdateRecordDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    private int day;
    private String checkInTime;
    private String checkOutTime;
    private String status;

    public Record(UpdateRecordDto updateRecord) {
        this.day = updateRecord.getDay();
        this.checkInTime = updateRecord.getCheckInTime();
        this.checkOutTime = updateRecord.getCheckOutTime();
        this.status = updateRecord.getStatus();
    }
}
