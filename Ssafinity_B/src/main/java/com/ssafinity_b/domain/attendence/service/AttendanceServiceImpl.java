package com.ssafinity_b.domain.attendence.service;

import com.ssafinity_b.domain.attendence.dto.*;
import com.ssafinity_b.domain.attendence.entity.Attendance;
import com.ssafinity_b.domain.attendence.entity.Record;
import com.ssafinity_b.domain.attendence.repository.AttendanceRepository;
import com.ssafinity_b.global.exception.AttendanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

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

    @Override
    public String create(CreateAttendanceDto createAttendanceDto) {
        Attendance attendance = new Attendance(createAttendanceDto.getMemberId(), createAttendanceDto.getYear(), createAttendanceDto.getMonth());
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return savedAttendance.getId();
    }

    @Override
    public GetAttendanceDto get(Long memberId, int year, int month) {
        Attendance attendance = attendanceRepository.findById(memberId+"-"+year+"-"+month).orElseThrow(()->
                new AttendanceNotFoundException("출석정보가 없습니다."));
        return new GetAttendanceDto(attendance);
    }

    @Transactional
    @Override
    public String update(UpdateRecordDto updateRecord) {
        Attendance attendance = attendanceRepository.findById(updateRecord.getId()).orElseThrow(()->
                new AttendanceNotFoundException("출석정보가 없습니다."));
        attendance.getRecordList().add(new Record(updateRecord));
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return updatedAttendance.getId();
    }

    @Override
    public void delete(Long memberId, int year, int month) {
        attendanceRepository.deleteById(memberId+"-"+year+"-"+month);
    }
}
