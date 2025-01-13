package com.ssafynity_b.global.fileupload.minio.service.impl;

import com.ssafynity_b.global.fileupload.minio.service.MinIoService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class MinIoServiceImpl implements MinIoService {

    private final MinioClient minioClient;

    @Value("${minio.url}")
    String URL;

    @Value("${minio.access-key}")
    String ACCESS_KEY;

    @Value("${minio.secret-key}")
    String SECRET_KEY;

    @Value("${minio.bucket-name}")
    String BUCKET_NAME;

    @Value("${minio.directory-path}")
    String DIRECTORY_PATH;

    @Override
    public void uploadFileToMinIO(CustomUserDetails userDetails, String fileName, InputStream inputStream, long contentLength) {
        //멤버 이름
        Long memberId = userDetails.getMember().getId();

        try {
            //MinIO버킷에 저장할 객체 생성
            //경로(-> user/멤버 이름/profile)에 이미지 저장
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

    @Override
    public void uploadFileToMinIOBySignUp(Long memberId, MultipartFile file) throws FileUploadException {

        try(InputStream inputStream = file.getInputStream()) {
            String fileName = file.getOriginalFilename();
            long contentLength = file.getSize();

            //MinIO버킷에 저장할 객체 생성
            //경로(-> user/멤버 이름/profile)에 이미지 저장
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
            throw new FileUploadException("파일 업로드 중 오류 발생",e);
        }
    }

    @Override
    public String getFileToMinIO(CustomUserDetails userDetails) throws IOException {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(URL) // MinIO 서버 URL
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();

            String objectName = "users/"+userDetails.getMember().getId()+"/profile";

            // GetObjectArgs를 사용하여 객체 요청 인자 구성
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .build();

            // 객체를 스트림으로 가져옴
            try (InputStream stream = minioClient.getObject(args)) {
                byte[] content = stream.readAllBytes();
                return Base64.encodeBase64String(content);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
            throw new RuntimeException("Failed to retrieve file from MinIO", e);
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
