package com.ssafynity_b.global.handler;

import com.ssafynity_b.global.exception.*;
import io.minio.errors.MinioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* member */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MemberCreationException.class)
    public ResponseEntity<?> handleMemberCreationException(MemberCreationException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("회원 생성 실패", e.getMessage()));
    }

    /* file */
    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<?> handleFileUploadException(FileUploadException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("파일 업로드 실패", e.getMessage()));
    }

    @ExceptionHandler(MinioException.class)
    public ResponseEntity<?> handleMinioException(MinioException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("프로필 사진을 찾지못했습니다",e.getMessage()));
    }

    /* attendance */
    @ExceptionHandler(CheckInException.class)
    public ResponseEntity<String> handleCheckInException(CheckInException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CheckOutException.class)
    public ResponseEntity<String> handleCheckOutException(CheckOutException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<String> handleAttendanceNotFoundException(AttendanceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /* board */
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> handleBoardNotFoundException(BoardNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /* message */
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleMessageNotFoundException(MessageNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
