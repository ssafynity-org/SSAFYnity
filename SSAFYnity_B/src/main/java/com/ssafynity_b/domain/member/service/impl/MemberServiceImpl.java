package com.ssafynity_b.domain.member.service.impl;

import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.domain.member.service.MemberService;
import com.ssafynity_b.global.exception.MemberCreationException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import com.ssafynity_b.global.fileupload.minio.service.MinIoService;
import com.ssafynity_b.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    //회원가입시 비밀번호를 암호화하는 PasswordEncoder
    private final BCryptPasswordEncoder passwordEncoder;

    //Member객체를 저장할 MemberRepository
    private final MemberRepository memberRepository;

    //파일 저장을 위한 MinIo서비스
    private final MinIoService minIoService;

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

    //로그인 성공시 회원정보와 저장된 프로필이미지를 반환하는 서비스
    @Override
    public GetLoginDto getLoginInformation(CustomUserDetails userDetails) throws IOException {
        //멤버 정보 조회
        Long memberId = userDetails.getMember().getId();
        //멤버가 존재하지않을 경우 예외발생(=>로그인 실패)
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        //저장된 프로필 이미지가 있을경우 해당 프로필 이미지도 같이 반환
        if(member.isProfileImage()) {
            String profileImage = minIoService.getFileToMinIO(userDetails);
            return new GetLoginDto(member, profileImage);
        }
        //없을경우 멤버 정보만 반환
        return new GetLoginDto(member);
    }

    //멤버 정보 수정 서비스
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

    //멤버 삭제 서비스
    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
