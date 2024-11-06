package com.ssafinity_b.domain.member.service;

import com.ssafinity_b.domain.member.dto.*;
import com.ssafinity_b.domain.member.entity.Member;
import com.ssafinity_b.domain.member.repository.MemberRepository;
import com.ssafinity_b.global.exception.MemberNotFounException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long createMember(CreateMemberDto memberDto) {
        Member member = Member.of(memberDto.getEmail(), memberDto.getPassword(), memberDto.getName());
        Member savedMember = memberRepository.save(member);
        return savedMember.getMemberId();
    }

    @Override
    public GetMemberDto getMember(Long memberId) {
        return new GetMemberDto(memberRepository.findById(memberId).orElse(null));
    }

    @Override
    public Long updateMember(UpdateMemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getMemberId()).orElseThrow(() ->
                new MemberNotFounException(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("회원을 찾을 수 없습니다.")));
        member.updateMember(memberDto);
        return member.getMemberId();
    }

    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
