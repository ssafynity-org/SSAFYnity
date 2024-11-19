package com.ssafynity_b.global.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException() {
        super(System.lineSeparator() + "[예외 메시지] Comment를 찾을 수 없습니다.");
    }

    public CommentNotFoundException(String errorMsg) {
        super(System.lineSeparator() + "[예외 메시지] " + errorMsg);
    }
}
