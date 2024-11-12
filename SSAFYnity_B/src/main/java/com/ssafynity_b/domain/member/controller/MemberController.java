package com.ssafynity_b.domain.member.controller;

import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Member 컨트롤러")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 생성")
    @PostMapping
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
}
