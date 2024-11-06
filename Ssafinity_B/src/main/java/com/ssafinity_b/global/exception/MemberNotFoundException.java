package com.ssafinity_b.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException(String message){
        super(message);
    }
}
