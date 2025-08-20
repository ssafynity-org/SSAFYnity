package com.ssafynity_b.global.s3;

import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class S3service {

    private final String bucket;
    private final String region;
    private final String cloudFrontUrl;
    private final S3Client s3Client;

    private final MemberRepository memberRepository;

    public S3service(
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.credentials.access-key}") String accessKey,
            @Value("${cloud.aws.credentials.secret-key}") String secretKey,
            @Value("${cloud.aws.region.static}") String region,
            @Value("${cloud.aws.cloudfront.url}") String cloudFrontUrl,
            MemberRepository memberRepository
    ) {
        this.bucket = bucket;
        this.region = region;
        this.cloudFrontUrl = cloudFrontUrl;
        this.memberRepository = memberRepository;
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    @Transactional
    public String uploadProfileImage(Long memberId, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = "profile/" + memberId + "/" + UUID.randomUUID() + ext;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        String profileImageUrl = cloudFrontUrl + fileName;
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.updateProfileImageUrl(profileImageUrl);
        return cloudFrontUrl + fileName;
    }

    public String getProfileImage(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.getProfileImageUrl();
    }

    public String uploadArticleImage(Long id, MultipartFile file) throws IOException{
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = "article/" + id + "/"+ UUID.randomUUID() + ext;

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

    public void deleteArticleImage(Long id) {
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix("article/" + id + "/")
                .build();
        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

        for (S3Object s3Object : listResponse.contents()) {
            s3Client.deleteObject(builder -> builder
                    .bucket(bucket)
                    .key(s3Object.key())
                    .build());
        }
    }

}
