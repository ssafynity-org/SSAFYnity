package com.ssafinity_b.domain.attendence.service;

import com.ssafinity_b.domain.attendence.dto.*;

public interface AttendanceService {

    void checkIn(CheckDto check);

    void checkOut(CheckDto check);

    String create(CreateAttendanceDto createAttendanceDto);

    GetAttendanceDto getAttendance(Long memberId, int year, int month);

    String update(UpdateRecordDto updateRecord);

    void delete(Long memberId, int year, int month);
}
