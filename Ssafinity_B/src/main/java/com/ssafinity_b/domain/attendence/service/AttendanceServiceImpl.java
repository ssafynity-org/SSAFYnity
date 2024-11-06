package com.ssafinity_b.domain.attendence.service;

import com.ssafinity_b.domain.attendence.dto.CheckDto;
import com.ssafinity_b.domain.attendence.entity.Attendance;
import com.ssafinity_b.domain.attendence.repository.AttendanceRepository;
import com.ssafinity_b.global.exception.CheckInException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private static final LocalTime CHECK_IN_START = LocalTime.of(8, 30);
    private static final LocalTime CHECK_IN_END = LocalTime.of(9, 0);
    private static final LocalTime CHECK_OUT_START = LocalTime.of(18, 0);
    private static final LocalTime CHECK_OUT_END = LocalTime.of(18, 30);

    @Override
    public void checkIn(CheckDto check) {

    }

    @Override
    public void checkOut(CheckDto check) {

    }
}
