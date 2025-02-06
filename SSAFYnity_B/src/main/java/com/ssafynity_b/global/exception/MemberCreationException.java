package com.ssafynity_b.global.exception;

//멤버 생성하던중 오류 발생
public class MemberCreationException extends RuntimeException{
    public MemberCreationException(String message, Exception e) {
        super(message, e);
    }
}
