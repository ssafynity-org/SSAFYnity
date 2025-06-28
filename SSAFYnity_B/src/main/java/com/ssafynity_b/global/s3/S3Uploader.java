package com.ssafynity_b.global.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class S3Uploader {

    private final String bucket;
    private final String region;
    private final String cloudFrontUrl;
    private final S3Client s3Client;

    public S3Uploader(
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.credentials.access-key}") String accessKey,
            @Value("${cloud.aws.credentials.secret-key}") String secretKey,
            @Value("${cloud.aws.region.static}") String region,
            @Value("${cloud.aws.cloudfront.url}") String cloudFrontUrl
    ) {
        this.bucket = bucket;
        this.region = region;
        this.cloudFrontUrl = cloudFrontUrl;
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    public String uploadArticleImage(Long articleId, MultipartFile file) throws IOException{
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = "article/" + articleId + "/"+ UUID.randomUUID() + ext;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return cloudFrontUrl + fileName;
    }

    public List<String> uploadArticleImageList(Long articleId, List<MultipartFile> fileList) throws IOException {
        List<String> urls = new ArrayList<>();

        for(MultipartFile file: fileList){
            String url = uploadArticleImage(articleId, file);
            urls.add(url);
        }
        return urls;
    }

}
