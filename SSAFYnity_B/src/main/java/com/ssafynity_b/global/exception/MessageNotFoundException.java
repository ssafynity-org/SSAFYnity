package com.ssafynity_b.global.exception;

public class MessageNotFoundException extends RuntimeException{

    public MessageNotFoundException(){
        super("쪽지를 찾을 수 없습니다.");
    }
}
