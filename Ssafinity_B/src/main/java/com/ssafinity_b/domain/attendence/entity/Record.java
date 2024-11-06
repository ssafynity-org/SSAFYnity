package com.ssafinity_b.domain.attendence.entity;

import com.ssafinity_b.domain.attendence.dto.UpdateRecordDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    private String checkInTime;
    private String checkOutTime;
    private String status;

    public Record(UpdateRecordDto updateRecord) {
        this.checkInTime = updateRecord.getCheckInTime();
        this.checkOutTime = updateRecord.getCheckOutTime();
        this.status = updateRecord.getStatus();
    }

    public void updateCheckOutTime(String checkOutTime){
        this.checkOutTime = checkOutTime;
    }

    public void updateStatus(String status){
        this.status = status;
    }

}
