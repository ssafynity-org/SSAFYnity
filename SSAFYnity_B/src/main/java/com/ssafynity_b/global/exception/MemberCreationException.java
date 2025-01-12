package com.ssafynity_b.global.exception;

public class MemberCreationException extends RuntimeException{
    public MemberCreationException(String message, Exception e) {
        super(message, e);
    }
}
