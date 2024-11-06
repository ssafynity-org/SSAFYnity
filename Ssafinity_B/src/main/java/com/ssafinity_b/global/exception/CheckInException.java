package com.ssafinity_b.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class CheckInException extends RuntimeException{

    private final ResponseEntity<?> responseEntity;

    public ResponseEntity<?> getResponseEntity(){
        return responseEntity;
    }
}
