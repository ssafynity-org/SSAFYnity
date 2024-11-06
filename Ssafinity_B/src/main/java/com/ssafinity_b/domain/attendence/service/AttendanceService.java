package com.ssafinity_b.domain.attendence.service;

import com.ssafinity_b.domain.attendence.dto.CheckDto;

public interface AttendanceService {

    void checkIn(CheckDto check);

    void checkOut(CheckDto check);
}
