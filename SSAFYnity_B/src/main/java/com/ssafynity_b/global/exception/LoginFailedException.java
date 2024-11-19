package com.ssafynity_b.global.exception;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String errorMsg) {
        super(System.lineSeparator() +"[예외 메세지] "+errorMsg);
    }
}
