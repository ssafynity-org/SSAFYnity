package com.ssafynity_b.domain.member.controller;

import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member 컨트롤러")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원정보 저장")
    @PostMapping(value = "/signup")
    public ResponseEntity<?> createMember(@RequestBody CreateMemberDto memberDto) throws FileUploadException {
        memberService.createMember(memberDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이메일 중복 확인")
    @GetMapping(value = "/signup/{email}")
    public ResponseEntity<?> checkEmailDuplicates(@PathVariable String email) {
        boolean response = memberService.checkEmailDuplicates(email);

        //중복된 이메일이 있을경우 NOT_FOUND
        if(response){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //없을경우 OK
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMember(@PathVariable Long id){
        GetMemberDto memberDto = memberService.getMember(id);
        return new ResponseEntity<GetMemberDto>(memberDto, HttpStatus.OK);
    }

    @Operation(summary = "회원 수정")
    @PutMapping
    public ResponseEntity<?> updateMember(@RequestBody UpdateMemberDto memberDto){
        Long memberId = memberService.updateMember(memberDto);
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
