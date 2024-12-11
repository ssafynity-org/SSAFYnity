package com.ssafynity_b.global.fileupload.minio.service.impl;

import com.ssafynity_b.global.fileupload.minio.service.MinIoUploadService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
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

@RequiredArgsConstructor
@Service
public class MinIoUploadServiceImpl implements MinIoUploadService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    String BUCKET_NAME;

    @Value("${minio.directory-path}")
    String DIRECTORY_PATH;

    @Override
    public void uploadFileToMinio(CustomUserDetails userDetails, String fileName, InputStream inputStream, long contentLength) {
        //멤버 이름
        Long memberId = userDetails.getMember().getId();

        try {
            //MinIO버킷에 저장할 객체 생성
            //경로(-> user/멤버 이름/fileName)에 이미지 저장
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(DIRECTORY_PATH + memberId + "/profile")
                    .stream(inputStream, contentLength, -1)
                    .contentType(getContentType(fileName))
                    .build();

            //MinIO버킷에 객체 저장
            minioClient.putObject(putObjectArgs);

        //오류 발생시 예외처리
        }catch (Exception e) {
            System.err.println("Other errors: " + e.getMessage());
        }
    }

    //파일 타입 추출(ex.jpg,webp,png,jpeg...)
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
