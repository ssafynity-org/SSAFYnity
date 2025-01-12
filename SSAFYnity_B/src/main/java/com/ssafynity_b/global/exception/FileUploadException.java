package com.ssafynity_b.global.exception;

public class FileUploadException extends RuntimeException{
    public FileUploadException(String message, Exception e) {
        super(message, e);
    }
}
