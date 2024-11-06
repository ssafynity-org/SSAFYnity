package com.ssafinity_b.domain.attendence.dto;

import com.ssafinity_b.domain.attendence.entity.Attendance;
import com.ssafinity_b.domain.attendence.entity.Record;
import lombok.*;

import java.util.List;
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
