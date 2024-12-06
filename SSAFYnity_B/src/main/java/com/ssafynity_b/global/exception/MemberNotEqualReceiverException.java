package com.ssafynity_b.global.exception;

public class MemberNotEqualReceiverException extends RuntimeException {
    public MemberNotEqualReceiverException() {
        super("현재 접속한 멤버가 수신자와 다릅니다.");
    }
}
