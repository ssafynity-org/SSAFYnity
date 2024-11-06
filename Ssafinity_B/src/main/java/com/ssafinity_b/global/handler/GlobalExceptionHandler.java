package com.ssafinity_b.global.handler;

import com.ssafinity_b.global.exception.AttendanceNotFoundException;
import com.ssafinity_b.global.exception.CheckInException;
import com.ssafinity_b.global.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CheckInException.class)
    public ResponseEntity<String> handleCheckInException(CheckInException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<String> handleAttendanceNotFoundException(AttendanceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
