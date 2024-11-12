package com.ssafynity_b.domain.member.service;

import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.entity.Member;
//import com.ssafinity_b.domain.member.repository.MemberDocumentRepository;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
//    private final MemberDocumentRepository documentRepository;

    @Transactional
    @Override
    public Long createMember(CreateMemberDto memberDto) {
        Member member = new Member(memberDto);
        Member savedMember = memberRepository.save(member);
        MemberDocument memberDocument = new MemberDocument(savedMember);
//        documentRepository.save(memberDocument);
//        System.out.println("저장된 문서 정보 : " + memberDocument.getCompany());
        return savedMember.getMemberId();
    }

    @Override
    public GetMemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return new GetMemberDto(member);
    }

    @Transactional
    @Override
    public Long updateMember(UpdateMemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
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

    @Override
    public List<GetMemberDto> getMemberByCompany(String keyword) {
//        System.out.println("keyword는 " + keyword);
//        List<MemberDocument> memberList = documentRepository.findByCompany(keyword);
//        System.out.println("찾은 건수는 " + memberList.size());
//        return memberList.stream()
//                .map(member -> new GetMemberDto(member.getMemberId(), member.getEmail(), member.getPassword(), member.getName(), member.getCompany()))
//                .toList();
        return List.of();
    }

}
