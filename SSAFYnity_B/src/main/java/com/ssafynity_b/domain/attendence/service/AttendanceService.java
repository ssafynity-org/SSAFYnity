package com.ssafynity_b.domain.attendence.service;

import com.ssafynity_b.domain.attendence.dto.*;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AttendanceService {

    void createDocument();

    String checkIn(CustomUserDetails userDetails);

    String checkOut(CustomUserDetails userDetails);

    String create(CustomUserDetails userDetails, CreateAttendanceDto createAttendanceDto);

    GetAttendanceDto getAttendance(CustomUserDetails userDetails, int year, int month);

    String update(CustomUserDetails userDetails, UpdateRecordDto updateRecord);

    void delete(CustomUserDetails userDetails, int year, int month);
}
