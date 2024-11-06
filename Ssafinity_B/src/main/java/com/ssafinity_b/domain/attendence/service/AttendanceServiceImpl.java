package com.ssafinity_b.domain.attendence.service;

import com.ssafinity_b.domain.attendence.dto.*;
import com.ssafinity_b.domain.attendence.entity.Attendance;
import com.ssafinity_b.domain.attendence.entity.Record;
import com.ssafinity_b.domain.attendence.repository.AttendanceRepository;
import com.ssafinity_b.domain.member.entity.Member;
import com.ssafinity_b.domain.member.repository.MemberRepository;
import com.ssafinity_b.global.exception.AttendanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private static final LocalTime CHECK_IN_START = LocalTime.of(8, 30);
    private static final LocalTime CHECK_IN_END = LocalTime.of(9, 0);
    private static final LocalTime CHECK_OUT_START = LocalTime.of(18, 0);
    private static final LocalTime CHECK_OUT_END = LocalTime.of(18, 30);
    private final MemberRepository memberRepository;

    /* 매월 1일 새벽 4시에 회원마다 그 월의 출결기록 문서 생성 */
    @Scheduled(cron = "0 0 4 1 * *")
    public void createAttendanceDocument() {
        LocalDateTime date = LocalDateTime.now();
        int year = date.getYear();
        int month = date.getMonthValue();

        List<Member> memberList = memberRepository.findAll();
        for(int i = 0;i<memberList.size();i++){
            Long memberId = memberList.get(i).getMemberId();
            Attendance attendance = new Attendance(memberId, year, month);
            Attendance savedAttendance = attendanceRepository.save(attendance);
        }
    }

    @Override
    public void checkIn(CheckDto check) {
        LocalDateTime localDateTime = LocalDateTime.parse(check.getDate(), DateTimeFormatter.ISO_DATE_TIME);
        Long memberId = check.getMemberId();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        String id = memberId + "-" + year + "-" + month;

        // 현재 멤버의 이번달 도큐먼트 조회
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(()->
                new AttendanceNotFoundException("출석정보가 없습니다."));

        // 입실시간
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = localDateTime.format(format);

        // 지각인지 아닌지 판별
        if(localDateTime.toLocalTime().isAfter(CHECK_IN_END) || localDateTime.toLocalTime().equals(CHECK_IN_END)){
            Record record = Record.builder()
                    .checkInTime(time)
                    .status("지각")
                    .build();
            attendance.getRecords().put(day, record);
        } else{
            Record record = Record.builder()
                    .checkInTime(time)
                    .build();
            attendance.getRecords().put(day, record);
        }

    }

    @Override
    public void checkOut(CheckDto check) {

        LocalDateTime localDateTime = LocalDateTime.parse(check.getDate(), DateTimeFormatter.ISO_DATE_TIME);
        Long memberId = check.getMemberId();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        String id = memberId + "-" + year + "-" + month;

        // 현재 멤버의 이번달 도큐먼트 조회
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(()->
                new AttendanceNotFoundException("출석정보가 없습니다."));

        // 퇴실시간
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = localDateTime.format(format);

        // 늦었는지 아닌지 판별
        if(localDateTime.toLocalTime().isAfter(CHECK_OUT_END) || localDateTime.toLocalTime().equals(CHECK_OUT_END)){
            attendance.getRecords().get(day).updateCheckOutTime(time);
            attendance.getRecords().get(day).updateStatus("조퇴");
        } else{
            attendance.getRecords().get(day).updateCheckOutTime(time);
        }

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
        attendance.getRecords().put(updateRecord.getDay(), new Record(updateRecord));
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return updatedAttendance.getId();
    }

    @Override
    public void delete(Long memberId, int year, int month) {
        attendanceRepository.deleteById(memberId+"-"+year+"-"+month);
    }


}
