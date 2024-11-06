package com.ssafinity_b.domain.attendence.controller;

import com.ssafinity_b.domain.attendence.dto.CheckDto;
import com.ssafinity_b.domain.attendence.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
