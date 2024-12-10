package com.ssafynity_b.domain.attendence.service;

import com.ssafynity_b.domain.attendence.dto.*;
import com.ssafynity_b.domain.attendence.document.Attendance;
import com.ssafynity_b.domain.attendence.document.Record;
import com.ssafynity_b.domain.attendence.repository.AttendanceRepository;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.AttendanceNotFoundException;
import com.ssafynity_b.global.exception.CheckInException;
import com.ssafynity_b.global.exception.CheckInNotFoundException;
import com.ssafynity_b.global.exception.CheckOutException;
import com.ssafynity_b.global.jwt.CustomUserDetails;
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

    //출석문서 레포지토리, 멤버 레포지토리
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    //서울 기준 서버시간, 입실 시작 시간, 입실 종료 시간, 퇴실 시작 시간, 퇴실 종료 시간, 멤버가 입실 또는 퇴실버튼 누른 시간
    private static final ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    private static final LocalTime CHECK_IN_START = LocalTime.of(8, 30);
    private static final LocalTime CHECK_IN_END = LocalTime.of(9, 0);
    private static final LocalTime CHECK_OUT_START = LocalTime.of(18, 0);
    private static final LocalTime CHECK_OUT_END = LocalTime.of(18, 30);

    // 입실하거나, 퇴실할때 출결 기록문서가 없으면 생성하는 로직을 포함시켰습니다.
    // 따라서 아래의 스케쥴러 로직이 필요없어졌습니다. 나중에 삭제해주세요.
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

    @Transactional
    @Override
    public String checkIn(CustomUserDetails userDetails) {

        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        // 입실시간 이전이면 실패 응답
        if(serverTime.toLocalTime().isBefore(CHECK_IN_START)){
            throw new CheckInException();
        }

        // 현재 멤버의 이번달 도큐먼트 조회 없으면 생성
        Attendance attendance = findAttendance(memberId);

        //입실 버튼 누른 시간, 오늘 날짜
        String time = serverTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        int day = serverTime.getDayOfMonth();

        // 지각인지 아닌지 판별
        if(serverTime.toLocalTime().isAfter(CHECK_IN_START) && serverTime.toLocalTime().equals(CHECK_IN_END)){
            Record record = Record.builder()
                    .checkInTime(time)
                    .status("출석 완료")
                    .build();
            attendance.getRecords().put(day, record);
            attendanceRepository.save(attendance);
            return "출석 완료";
        } else{
            Record record = Record.builder()
                    .checkInTime(time)
                    .build();
            attendance.getRecords().put(day, record);
            attendanceRepository.save(attendance);
            return "아직 입실 시간이 아니거나, 지났습니다.";
        }

    }

    @Override
    public String checkOut(CustomUserDetails userDetails) {

        //Jwt토큰에서 발견한 멤버
        Member member = userDetails.getMember();
        Long memberId = member.getId();

        // 퇴실시간 이전이면 실패 응답
        if(serverTime.toLocalTime().isBefore(CHECK_OUT_START)){
            throw new CheckOutException();
        }

        // 현재 멤버의 이번달 도큐먼트 조회 없으면 생성
        Attendance attendance = findAttendance(memberId);


        //퇴실 버튼 누른 시간, 오늘 날짜
        String time = serverTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        int day = serverTime.getDayOfMonth();

        //입실 버튼을 누른적이 없는데, 퇴실 버튼을 눌렀으면 예외발생
        if(attendance.getRecords().get(day)==null)
            throw new CheckInNotFoundException();

        // 늦었는지 아닌지 판별
        if(serverTime.toLocalTime().isAfter(CHECK_OUT_START) && serverTime.toLocalTime().isBefore(CHECK_OUT_END)){
            attendance.getRecords().get(day).updateCheckOutTime(time);
            attendance.getRecords().get(day).updateStatus("퇴실");
            attendanceRepository.save(attendance);
            return "퇴실";
        } else{
            attendance.getRecords().get(day).updateCheckOutTime(time);
            return "아직 퇴실시간이 아니거나, 지났습니다.";
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

    private Attendance findAttendance(Long memberId) {

        //서울기준 년,월 추출
        int year = serverTime.getYear();
        int month = serverTime.getMonthValue();

        //출석 문서의 Id조합(멤버아이디 + 년 + 월)
        String id = memberId + "-" + year + "-" + month;

        //출석 문서 조회 없으면 생성
        Attendance attendance;
        try {
            attendance = attendanceRepository.findById(id).orElseThrow(AttendanceNotFoundException::new);
        }catch(Exception e){
            e.printStackTrace();
            attendance = new Attendance(memberId, year, month);
            attendanceRepository.save(attendance);
        }
        return attendance;
    }

}
