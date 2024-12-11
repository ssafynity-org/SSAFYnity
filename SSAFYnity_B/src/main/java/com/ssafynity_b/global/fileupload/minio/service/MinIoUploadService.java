package com.ssafynity_b.global.fileupload.minio.service;

import com.ssafynity_b.global.jwt.CustomUserDetails;

import java.io.InputStream;

public interface MinIoUploadService {
    public void uploadFileToMinio(CustomUserDetails userDetails, String fileName, InputStream inputStream, long contentLength);
}
