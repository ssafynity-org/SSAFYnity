package com.ssafinity_b.domain.member.service;

import com.ssafinity_b.domain.member.dto.*;
import com.ssafinity_b.domain.member.entity.Member;
import com.ssafinity_b.domain.member.repository.MemberRepository;
import com.ssafinity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberRepository.findById(memberId).orElseThrow(()->
                new MemberNotFoundException("회원을 찾을 수 없습니다."));
        return new GetMemberDto(member);
    }

    @Transactional
    @Override
    public Long updateMember(UpdateMemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getMemberId()).orElseThrow(() ->
                new MemberNotFoundException("회원을 찾을 수 없습니다."));
        member.updateMember(memberDto);
//        member.updateEmail(memberDto.getEmail())
//                .updateName(memberDto.getName())
//                .updatePassword(memberDto.getPassword());
        return member.getMemberId();
    }

    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
