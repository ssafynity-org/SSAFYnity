package com.ssafynity_b.domain.attendence.service;

import com.ssafynity_b.domain.attendence.dto.*;
import com.ssafynity_b.domain.attendence.document.Attendance;
import com.ssafynity_b.domain.attendence.document.Record;
import com.ssafynity_b.domain.attendence.repository.AttendanceRepository;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.AttendanceNotFoundException;
import com.ssafynity_b.global.exception.CheckInException;
import com.ssafynity_b.global.exception.CheckOutException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            Long memberId = memberList.get(i).getId();
            Attendance attendance = new Attendance(memberId, year, month);
            Attendance savedAttendance = attendanceRepository.save(attendance);
        }
    }

    @Override
    public String checkIn(CheckDto check) {

        ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        // 입실시간 이전이면 실패 응답
        if(serverTime.toLocalTime().isBefore(CHECK_IN_START)){
            throw new CheckInException();
        }

        Long memberId = check.getMemberId();
        int year = serverTime.getYear();
        int month = serverTime.getMonthValue();
        int day = serverTime.getDayOfMonth();
        String id = memberId + "-" + year + "-" + month;

        // 현재 멤버의 이번달 도큐먼트 조회
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(AttendanceNotFoundException::new);

        // 입실시간
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = serverTime.format(format);

        // 지각인지 아닌지 판별
        if(serverTime.toLocalTime().isAfter(CHECK_IN_END) || serverTime.toLocalTime().equals(CHECK_IN_END)){
            Record record = Record.builder()
                    .checkInTime(time)
                    .status("지각")
                    .build();
            attendance.getRecords().put(day, record);
            return "지각";
        } else{
            Record record = Record.builder()
                    .checkInTime(time)
                    .build();
            attendance.getRecords().put(day, record);
            return "정상 출결";
        }

    }

    @Override
    public String checkOut(CheckDto check) {

        ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        // 퇴실시간 이전이면 실패 응답
        if(serverTime.toLocalTime().isBefore(CHECK_OUT_START)){
            throw new CheckOutException();
        }

        Long memberId = check.getMemberId();
        int year = serverTime.getYear();
        int month = serverTime.getMonthValue();
        int day = serverTime.getDayOfMonth();
        String id = memberId + "-" + year + "-" + month;

        // 현재 멤버의 이번달 도큐먼트 조회
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(AttendanceNotFoundException::new);

        // 퇴실시간
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = serverTime.format(format);

        // 늦었는지 아닌지 판별
        if(serverTime.toLocalTime().isAfter(CHECK_OUT_END) || serverTime.toLocalTime().equals(CHECK_OUT_END)){
            attendance.getRecords().get(day).updateCheckOutTime(time);
            attendance.getRecords().get(day).updateStatus("조퇴");
            return "조퇴";
        } else{
            attendance.getRecords().get(day).updateCheckOutTime(time);
            return "퇴실";
        }

    }

    @Override
    public String create(CreateAttendanceDto createAttendanceDto) {
        Attendance attendance = new Attendance(createAttendanceDto.getMemberId(), createAttendanceDto.getYear(), createAttendanceDto.getMonth());
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return savedAttendance.getId();
    }

    @Override
    public GetAttendanceDto getAttendance(Long memberId, int year, int month) {
        Attendance attendance = attendanceRepository.findById(memberId+"-"+year+"-"+month).orElseThrow(AttendanceNotFoundException::new);
        return new GetAttendanceDto(attendance);
    }

    @Transactional
    @Override
    public String update(UpdateRecordDto updateRecord) {
        Attendance attendance = attendanceRepository.findById(updateRecord.getId()).orElseThrow(AttendanceNotFoundException::new);
        attendance.getRecords().put(updateRecord.getDay(), new Record(updateRecord));
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return updatedAttendance.getId();
    }

    @Override
    public void delete(Long memberId, int year, int month) {
        attendanceRepository.deleteById(memberId+"-"+year+"-"+month);
    }


}
