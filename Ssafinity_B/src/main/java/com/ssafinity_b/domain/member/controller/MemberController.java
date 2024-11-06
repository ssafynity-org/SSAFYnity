package com.ssafinity_b.domain.member.controller;

import com.ssafinity_b.domain.member.dto.CreateMemberDto;
import com.ssafinity_b.domain.member.dto.GetMemberDto;
import com.ssafinity_b.domain.member.dto.UpdateMemberDto;
import com.ssafinity_b.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 생성")
    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody CreateMemberDto memberDto){
        Long memberId = memberService.createMember(memberDto);
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable Long memberId){
        GetMemberDto memberDto = memberService.getMember(memberId);
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
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
        return new ResponseEntity<Long>(memberId, HttpStatus.OK);
    }
}
