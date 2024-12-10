package com.ssafynity_b.global.fileupload.minio.service;

import java.io.InputStream;

public interface MinIoUploadService {
    public void uploadFileToMinio(String fileName, InputStream inputStream, long contentLength);
}
