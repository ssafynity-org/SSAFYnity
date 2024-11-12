package com.ssafynity_b.global.exception;

public class CheckInException extends RuntimeException{

    public CheckInException(){
        super("입실 시간이 아닙니다.");
    }
}
