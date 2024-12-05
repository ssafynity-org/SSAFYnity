package com.ssafynity_b.domain.attendence.controller;

import com.ssafynity_b.domain.attendence.dto.*;
import com.ssafynity_b.domain.attendence.service.AttendanceService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance 컨트롤러")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "입실체크")
    @PostMapping("/in")
    public ResponseEntity<?> checkIn(@AuthenticationPrincipal CustomUserDetails userDetails){
        System.out.println(userDetails);
        String response = attendanceService.checkIn(userDetails);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "퇴실체크")
    @PostMapping("/out")
    public ResponseEntity<?> checkOut(@AuthenticationPrincipal CustomUserDetails userDetails){
        String response = attendanceService.checkOut(userDetails);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "월별 출결 기록 조회")
    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> get(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int year, @PathVariable int month){
        GetAttendanceDto attendance = attendanceService.getAttendance(userDetails, year, month);
        return new ResponseEntity<GetAttendanceDto>(attendance, HttpStatus.OK);
    }




    /* 테스트를 위한 CRUD */

    @Operation(summary = "출결문서 생성")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateAttendanceDto createAttendanceDto){
        String id = attendanceService.create(userDetails ,createAttendanceDto);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @Operation(summary = "출결문서 수정")
    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateRecordDto updateRecord){
        String id = attendanceService.update(userDetails ,updateRecord);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @Operation(summary = "출결문서 삭제")
    @DeleteMapping("/{year}/{month}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int year, @PathVariable int month) {
        attendanceService.delete(userDetails, year, month);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
