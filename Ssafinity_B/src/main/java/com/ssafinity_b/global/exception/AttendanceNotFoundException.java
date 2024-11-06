package com.ssafinity_b.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class AttendanceNotFoundException extends RuntimeException{

    public AttendanceNotFoundException(String message){
        super(message);
    }
}
