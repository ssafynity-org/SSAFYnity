package com.ssafynity_b.domain.member.service.impl;

import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.domain.member.service.MemberService;
import com.ssafynity_b.global.exception.MemberCreationException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    //회원가입시 비밀번호를 암호화하는 PasswordEncoder
    private final BCryptPasswordEncoder passwordEncoder;

    //Member객체를 저장할 MemberRepository
    private final MemberRepository memberRepository;

    //멤버생성 서비스
    @Override
    public void createMember(CreateMemberDto memberDto) {
        try {
            //회원 생성
            Member.MemberBuilder memberBuilder = Member.builder()
                    .name(memberDto.getName())
                    .email(memberDto.getEmail())
                    .password(passwordEncoder.encode(memberDto.getPassword()))
                    .cohort(memberDto.getCohort())
                    .campus(memberDto.getCampus())
                    .jobSearch(memberDto.isJobSearch())
                    .profileImage(false)
                    .role("ROLE_USER");

            if (memberDto.isJobSearch()) { //취업준비중일경우(회사명 취준, 회사 비공개상태로 등록)
                memberBuilder.company("취준")
                        .companyBlind(true);
            } else { //재직중일경우(재직중인 회사명, 회사 공개상태로 등록)
                memberBuilder.company(memberDto.getCompany())
                        .companyBlind(memberDto.getCompanyBlind());
            }

            Member member = memberBuilder.build();
            memberRepository.save(member);

        }catch(Exception e){
            throw new MemberCreationException(e.getMessage(),e);
        }
    }

    //이메일 중복체크 서비스
    @Override
    public boolean checkEmailDuplicates(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    //회원조회 서비스
    @Override
    public GetMemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return new GetMemberDto(member);
    }

    //멤버 정보 수정 서비스
    @Transactional
    @Override
    public Long updateMember(UpdateMemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        member.updateEmail(memberDto.getEmail())
                .updatePassword(memberDto.getPassword())
                .updateName(memberDto.getName())
                .updateCompany(memberDto.getCompany());
        return member.getId();
    }

    //멤버 삭제 서비스
    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
