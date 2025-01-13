package com.ssafynity_b.domain.member.controller;

import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.service.MemberService;
import com.ssafynity_b.global.fileupload.minio.service.MinIoService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
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
    private final MinIoService minioService;

    @Operation(summary = "회원 생성")
    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody CreateMemberDto memberDto){
        Long memberId = memberService.createMember(memberDto);
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
    }

    @Operation(summary = "회원정보 및 프로필사진 저장")
    @PostMapping(value = "/signup/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMemberAndProfileImage(@RequestPart("member")CreateMemberDto memberDto, @RequestPart("file")MultipartFile profileImage) throws FileUploadException {
        memberService.createMemberAndProfileImage(memberDto, profileImage);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
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

    @Operation(summary = "로그인 회원 정보 및 프로필이미지 조회")
    @GetMapping("/login/member")
    public ResponseEntity<GetLoginDto> getLoginInformation(@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        GetLoginDto loginDto = memberService.getLoginInformation(userDetails);
        return new ResponseEntity<>(loginDto,HttpStatus.OK);
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

    //localhost:9000으로 접속해서 확인 가능
    //아이디 : dldnwls009 비밀번호 : dldnwls009
    @Operation(summary = "프로필이미지 저장(MinIO,Multipartfile방식)")
    @PostMapping(value = "/upload/profileImage/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveMultipartToMinIO(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestPart("file")MultipartFile file){
        try(InputStream inputStream = file.getInputStream()){
            String fileName = file.getOriginalFilename();
            long contentLength = file.getSize();

            System.out.println("userDetails : " + userDetails);
            System.out.println("fileName : " + fileName);
            System.out.println("inputStream : " + inputStream);
            System.out.println("contentLength : " + contentLength);

            minioService.uploadFileToMinIO(userDetails, fileName, inputStream, contentLength);
            return "업로드 완료";
        } catch(IOException e){
            return "업로드 실패 : " + e.getMessage();
        }
    }

    @Operation(summary = "프로필이미지 저장(MinIO,Stream방식)")
    @PostMapping(value = "/upload/profileImage/stream", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveStreamToMinIO(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request){
        try(InputStream inputStream = request.getInputStream()){
            String fileName = request.getHeader("file-name");
            long contentLength = request.getContentLengthLong();

            System.out.println("userDetails : " + userDetails);
            System.out.println("fileName : " + fileName);
            System.out.println("inputStream : " + inputStream);
            System.out.println("contentLength : " + contentLength);

            minioService.uploadFileToMinIO(userDetails, fileName, inputStream, contentLength);
            return "업로드 완료";
        } catch(IOException e){
            return "업로드 실패 : " + e.getMessage();
        }
    }
}
