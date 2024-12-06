package com.ssafynity_b.global.exception;

public class CheckInNotFoundException extends RuntimeException{
    public CheckInNotFoundException() {
        super("아직 입실하지않았습니다. 입실 버튼부터 눌러주세요.");
    }
}
