package com.ssafinity_b.domain.attendence.controller;

import com.ssafinity_b.domain.attendence.dto.*;
import com.ssafinity_b.domain.attendence.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/in")
    public ResponseEntity<?> checkIn(@RequestBody CheckDto check){
        attendanceService.checkIn(check);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/out")
    public ResponseEntity<?> checkOut(@RequestBody CheckDto check){
        attendanceService.checkOut(check);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAttendanceDto createAttendanceDto){
        String id = attendanceService.create(createAttendanceDto);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @GetMapping("/{memberId}/{year}/{month}")
    public ResponseEntity<?> get(@PathVariable Long memberId, @PathVariable int year, @PathVariable int month){
        GetAttendanceDto attendance = attendanceService.get(memberId, year, month);
        return new ResponseEntity<GetAttendanceDto>(attendance, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateRecordDto updateRecord){
        String id = attendanceService.update(updateRecord);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}/{year}/{month}")
    public ResponseEntity<?> delete(@PathVariable Long memberId, @PathVariable int year, @PathVariable int month) {
        attendanceService.delete(memberId, year, month);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
