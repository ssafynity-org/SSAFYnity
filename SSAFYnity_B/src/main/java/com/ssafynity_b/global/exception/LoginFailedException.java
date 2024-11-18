package com.ssafynity_b.global.exception;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super();
    }

    public LoginFailedException(Exception ex) {
        super(System.lineSeparator() + ex.getMessage());
    }
}
