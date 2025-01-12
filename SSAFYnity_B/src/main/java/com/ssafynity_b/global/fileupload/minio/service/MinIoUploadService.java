package com.ssafynity_b.global.fileupload.minio.service;

import com.ssafynity_b.domain.member.dto.CreateMemberDto;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinIoUploadService {
    public void uploadFileToMinIO(CustomUserDetails userDetails, String fileName, InputStream inputStream, long contentLength);

    public void uploadFileToMinIoBySignUp(Long memberId, MultipartFile file) throws FileUploadException;
}
