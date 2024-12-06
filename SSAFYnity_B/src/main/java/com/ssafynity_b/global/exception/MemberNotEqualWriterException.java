package com.ssafynity_b.global.exception;

public class MemberNotEqualWriterException extends RuntimeException{
    public MemberNotEqualWriterException() {
        super("접근한 멤버와 글쓴이가 다릅니다.");
    }
}
