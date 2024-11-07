package com.ssafinity_b.global.exception;

public class CheckOutException extends RuntimeException{

    public CheckOutException(){
        super("퇴실 시간이 아닙니다.");
    }
}
