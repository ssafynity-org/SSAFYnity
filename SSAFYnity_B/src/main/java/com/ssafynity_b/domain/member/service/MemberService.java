package com.ssafynity_b.domain.member.service;

import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    void createMember(CreateMemberDto memberDto);

    boolean checkEmailDuplicates(String email);

    GetMemberDto getMember(Long memberId);

    Long updateMember(UpdateMemberDto memberDto);

    void deleteMember(Long memberId);

}
