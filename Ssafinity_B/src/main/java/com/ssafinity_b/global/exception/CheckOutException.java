package com.ssafinity_b.global.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckOutException extends RuntimeException{

    public CheckOutException(String message){
        super(message);
    }
}
