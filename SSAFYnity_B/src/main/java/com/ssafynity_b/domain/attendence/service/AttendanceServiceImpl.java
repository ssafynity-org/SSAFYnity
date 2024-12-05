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
import com.ssafynity_b.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public void createDocument() {
        createAttendanceDocument();
    }

    @Override
    public String checkIn(CustomUserDetails userDetails) {

        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();
        ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        // 입실시간 이전이면 실패 응답
        if(serverTime.toLocalTime().isBefore(CHECK_IN_START)){
            throw new CheckInException();
        }

        //현재 서버 시간, 월, 일, 도큐먼트ID
        int year = serverTime.getYear();
        int month = serverTime.getMonthValue();
        int day = serverTime.getDayOfMonth();
        String id = memberId + "-" + year + "-" + month;
        System.out.println("id뭐라찍히냐?: " + id);

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
    public String checkOut(CustomUserDetails userDetails) {

        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        // 퇴실시간 이전이면 실패 응답
        if(serverTime.toLocalTime().isBefore(CHECK_OUT_START)){
            throw new CheckOutException();
        }

        //현재 서버 시간, 월, 일, 도큐먼트ID
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
    public String create(CustomUserDetails userDetails, CreateAttendanceDto createAttendanceDto) {
        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        //생성하고자하는 출결문서의 년, 월
        int year = createAttendanceDto.getYear();
        int month = createAttendanceDto.getMonth();

        //출결 문서 생성 및 저장
        Attendance attendance = new Attendance(memberId, year, month);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return savedAttendance.getId();
    }

    @Override
    public GetAttendanceDto getAttendance(CustomUserDetails userDetails, int year, int month) {
        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        //출결문서 조회
        Attendance attendance = attendanceRepository.findById(memberId+"-"+year+"-"+month).orElseThrow(AttendanceNotFoundException::new);
        return new GetAttendanceDto(attendance);
    }

    @Transactional
    @Override
    public String update(CustomUserDetails userDetails, UpdateRecordDto updateRecord) {
        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        //수정할 년도, 수정할 달
        int year = updateRecord.getYear();
        int month = updateRecord.getMonth();

        //출결문서 수정
        Attendance attendance = attendanceRepository.findById(memberId+"-"+year+"-"+month).orElseThrow(AttendanceNotFoundException::new);
        attendance.getRecords().put(updateRecord.getDay(), new Record(updateRecord));

        //이 아래부분은 영속성 컨텍스트때문에 지워도 되지않나..? 나중에 혜선이가 발견하게되면 알아봐줘
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return updatedAttendance.getId();
    }

    @Override
    public void delete(CustomUserDetails userDetails, int year, int month) {
        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        //출결문서 삭제
        attendanceRepository.deleteById(memberId+"-"+year+"-"+month);
    }


}
