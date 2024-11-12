package com.ssafynity_b.domain.attendence.dto;

import com.ssafynity_b.domain.attendence.document.Attendance;
import com.ssafynity_b.domain.attendence.document.Record;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAttendanceDto {

    private String id;
    private Long memberId;
    private int year;
    private int month;
    private Map<Integer, Record> records;

    public GetAttendanceDto(Attendance attendance){
        this.id = attendance.getId();
        this.memberId = attendance.getMemberId();
        this.year = attendance.getYear();
        this.month = attendance.getMonth();
        this.records = attendance.getRecords();
    }
}
