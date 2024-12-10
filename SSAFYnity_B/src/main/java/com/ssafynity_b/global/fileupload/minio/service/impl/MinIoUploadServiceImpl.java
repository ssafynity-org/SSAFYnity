package com.ssafynity_b.global.fileupload.minio.service.impl;

import com.ssafynity_b.global.fileupload.minio.service.MinIoUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MinIoUploadServiceImpl implements MinIoUploadService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    String BUCKET_NAME;

    @Value("${minio.directory-path}")
    String DIRECTORY_PATH;

    @Override
    public void uploadFileToMinio(String fileName, InputStream inputStream, long contentLength) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(DIRECTORY_PATH + UUID.randomUUID() + fileName)
                    .stream(inputStream, contentLength, -1)
                    .contentType(getContentType(fileName))
                    .build();

            minioClient.putObject(putObjectArgs);
        }catch (Exception e) {
            System.err.println("Other errors: " + e.getMessage());
        }
    }

    private String getContentType(String fileName){
        Path path = Paths.get(fileName);
        String contentType = null;
        try{
            contentType = Files.probeContentType(path);
        } catch(IOException e){
            System.out.println("Error determining content type : "+ e.getMessage());
        }
        return contentType;
    }
}
