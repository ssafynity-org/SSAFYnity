package com.ssafynity_b.domain.attendence.controller;

import com.ssafynity_b.domain.attendence.dto.*;
import com.ssafynity_b.domain.attendence.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance 컨트롤러")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "입실체크")
    @PostMapping("/in")
    public ResponseEntity<?> checkIn(@RequestBody CheckDto check){
        String response = attendanceService.checkIn(check);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "퇴실체크")
    @PostMapping("/out")
    public ResponseEntity<?> checkOut(@RequestBody CheckDto check){
        String response = attendanceService.checkOut(check);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "월별 출결 기록 조회")
    @GetMapping("/{memberId}/{year}/{month}")
    public ResponseEntity<?> get(@PathVariable Long memberId, @PathVariable int year, @PathVariable int month){
        GetAttendanceDto attendance = attendanceService.getAttendance(memberId, year, month);
        return new ResponseEntity<GetAttendanceDto>(attendance, HttpStatus.OK);
    }




    /* 테스트를 위한 CRUD */

    @Operation(summary = "출결문서 생성")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAttendanceDto createAttendanceDto){
        String id = attendanceService.create(createAttendanceDto);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @Operation(summary = "출결문서 수정")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateRecordDto updateRecord){
        String id = attendanceService.update(updateRecord);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @Operation(summary = "출결문서 삭제")
    @DeleteMapping("/{memberId}/{year}/{month}")
    public ResponseEntity<?> delete(@PathVariable Long memberId, @PathVariable int year, @PathVariable int month) {
        attendanceService.delete(memberId, year, month);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
