package com.ssafinity_b.global.exception;

public class BoardNotFoundException extends RuntimeException{

    public BoardNotFoundException(){
        super("게시물을 찾을 수 없습니다.");
    }
}
