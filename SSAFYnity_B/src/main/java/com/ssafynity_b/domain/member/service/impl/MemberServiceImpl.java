package com.ssafynity_b.domain.member.service.impl;

import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberDocumentRepository;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.domain.member.service.MemberService;
import com.ssafynity_b.global.exception.LoginFailedException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberDocumentRepository documentRepository;

    @Transactional
    @Override
    public Long createMember(CreateMemberDto memberDto) {
        Member member = new Member(memberDto.getEmail(), passwordEncoder.encode(memberDto.getPassword()), memberDto.getName(), memberDto.getCompany());
        Member savedMember = memberRepository.save(member);
        MemberDocument memberDocument = new MemberDocument(savedMember.getId(), savedMember.getEmail(), savedMember.getPassword(), savedMember.getName(), savedMember.getCompany());
        documentRepository.save(memberDocument);
        return savedMember.getId();
    }

    @Override
    public GetMemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return new GetMemberDto(member);
    }

    @Override
    public List<GetMemberDto> getAllMember() {
        Iterable<MemberDocument> memberDocumentList = documentRepository.findAll();
        return StreamSupport.stream(memberDocumentList.spliterator(), false)
                .map(member -> new GetMemberDto(member.getMemberId(), member.getEmail(), member.getPassword(), member.getName(), member.getCompany()))
                .toList();
    }

    @Transactional
    @Override
    public Long updateMember(UpdateMemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        member.updateEmail(memberDto.getEmail())
                .updatePassword(memberDto.getPassword())
                .updateName(memberDto.getName())
                .updatdCompany(memberDto.getCompany());
        return member.getId();
    }

    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public List<GetMemberDto> searchMemberByCompany(String keyword) throws IOException {
        List<MemberDocument> memberList = documentRepository.searchByCompany(keyword);
        return memberList.stream()
                .map(member -> new GetMemberDto(member.getMemberId(), member.getEmail(), member.getPassword(), member.getName(), member.getCompany()))
                .toList();
    }

    @Override
    public List<GetMemberDto> searchMemberByName(String keyword) {
        List<MemberDocument> memberList = documentRepository.findByName(keyword);
        return memberList.stream()
                .map(member -> new GetMemberDto(member.getMemberId(), member.getEmail(), member.getPassword(), member.getName(), member.getCompany()))
                .toList();
    }

}
