package com.ssafynity_b.global.fileupload.minio.service;

import com.ssafynity_b.global.jwt.CustomUserDetails;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface MinIoService {
    public void uploadFileToMinIO(CustomUserDetails userDetails, String fileName, InputStream inputStream, long contentLength);

    public void uploadFileToMinIOBySignUp(Long memberId, MultipartFile file) throws FileUploadException;

    public String getFileToMinIO(CustomUserDetails userDetails) throws IOException;
}
