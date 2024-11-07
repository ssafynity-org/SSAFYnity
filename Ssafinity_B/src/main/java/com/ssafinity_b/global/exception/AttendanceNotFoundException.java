package com.ssafinity_b.global.exception;

public class AttendanceNotFoundException extends RuntimeException{

    public AttendanceNotFoundException(){
        super("출결정보를 찾을 수 없습니다.");
    }
}
