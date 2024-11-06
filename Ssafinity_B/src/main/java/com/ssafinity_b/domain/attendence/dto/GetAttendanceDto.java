package com.ssafinity_b.domain.attendence.dto;

import com.ssafinity_b.domain.attendence.entity.Attendance;
import com.ssafinity_b.domain.attendence.entity.Record;
import lombok.*;

import java.util.List;

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
    private List<Record> recordList;

    public GetAttendanceDto(Attendance attendance){
        this.id = attendance.getId();
        this.memberId = attendance.getMemberId();
        this.year = attendance.getYear();
        this.month = attendance.getMonth();
        this.recordList = attendance.getRecordList();
    }
}
