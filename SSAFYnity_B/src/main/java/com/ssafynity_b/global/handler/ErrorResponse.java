package com.ssafynity_b.global.handler;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message; //클라이언트에게 보여줄 메세지

    private String details; //에러의 추가적인 정보

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
}
