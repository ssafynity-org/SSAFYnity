package com.ssafynity_b.domain.member.controller;

import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.service.MemberService;
import com.ssafynity_b.global.fileupload.minio.service.MinIoUploadService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member 컨트롤러")
public class MemberController {

    private final MemberService memberService;
    private final MinIoUploadService minioUploadService;

    @Operation(summary = "회원 생성")
    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody CreateMemberDto memberDto){
        Long memberId = memberService.createMember(memberDto);
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
    }

    @Operation(summary = "회원 여러명 생성")
    @PostMapping("/all")
    public ResponseEntity<?> createMemberAll(@RequestBody List<CreateMemberDto> memberDto){
        for(int i = 0;i<memberDto.size();i++){
            memberService.createMember(memberDto.get(i));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable Long memberId){
        GetMemberDto memberDto = memberService.getMember(memberId);
        return new ResponseEntity<GetMemberDto>(memberDto, HttpStatus.OK);
    }

    @Operation(summary = "회원 전체 조회")
    @GetMapping
    public ResponseEntity<?> getMemberAll(){
        List<GetMemberDto> memberList = memberService.getAllMember();
        return new ResponseEntity<List<GetMemberDto>>(memberList, HttpStatus.OK);
    }

    @Operation(summary = "회원 수정")
    @PutMapping
    public ResponseEntity<?> updateMember(@RequestBody UpdateMemberDto memberDto){
        Long memberId = memberService.updateMember(memberDto);
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회사명으로 회원 검색")
    @GetMapping("/company/{keyword}")
    public ResponseEntity<?> getMemberByCompany(@PathVariable String keyword) throws IOException {
        List<GetMemberDto> memberList = memberService.searchMemberByCompany(keyword);
        return new ResponseEntity<List<GetMemberDto>>(memberList, HttpStatus.OK);
    }

    @Operation(summary = "이름으로 회원 검색")
    @GetMapping("/name/{keyword}")
    public ResponseEntity<?> getMemberByName(@PathVariable String keyword) throws IOException {
        List<GetMemberDto> memberList = memberService.searchMemberByName(keyword);
        return new ResponseEntity<List<GetMemberDto>>(memberList, HttpStatus.OK);
    }

    @Operation(summary = "프로필이미지 저장(MinIO,Multipartfile방식)")
    @PostMapping(value = "/upload/profileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveProfileImageByMinIO(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestPart("file")MultipartFile file){
        try(InputStream inputStream = file.getInputStream()){
            String fileName = file.getOriginalFilename();
            long contentLength = file.getSize();

            minioUploadService.uploadFileToMinio(userDetails, fileName, inputStream, contentLength);
            return "업로드 완료";
        } catch(IOException e){
            return "업로드 실패 : " + e.getMessage();
        }
    }
}
