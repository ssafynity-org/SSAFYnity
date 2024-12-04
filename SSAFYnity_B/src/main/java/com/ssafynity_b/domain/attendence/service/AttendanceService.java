package com.ssafynity_b.domain.attendence.service;

import com.ssafynity_b.domain.attendence.dto.*;
import com.ssafynity_b.global.jwt.CustomUserDetails;

public interface AttendanceService {

    void createDocument();

    String checkIn(CustomUserDetails userDetails);

    String checkOut(CheckDto check);

    String create(CreateAttendanceDto createAttendanceDto);

    GetAttendanceDto getAttendance(Long memberId, int year, int month);

    String update(UpdateRecordDto updateRecord);

    void delete(Long memberId, int year, int month);
}
