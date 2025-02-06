package com.ssafynity_b.domain.member.service.impl;

import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberDocumentRepository;
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
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    //회원가입시 비밀번호를 암호화하는 PasswordEncoder
    private final BCryptPasswordEncoder passwordEncoder;

    //Member객체를 저장할 MemberRepository
    private final MemberRepository memberRepository;

    //출석 현황을 저장할 documentRepository
    private final MemberDocumentRepository documentRepository;

    //파일 저장을 위한 MinIo서비스
    private final MinIoService minIoService;


    //회원가입
    @Transactional
    @Override
    public void createMemberAndProfileImage(CreateMemberDto memberDto, MultipartFile file) {
        try{
            //비어있는 회원 생성
            Member member;

            //생성자 주입
            if(memberDto.isJobSearch()){ //취업준비중일경우
                member = Member.builder()
                        .email(memberDto.getEmail())
                        .password(passwordEncoder.encode(memberDto.getPassword()))
                        .cohort(memberDto.getCohort())
                        .campus(memberDto.getCampus())
                        .jobSearch(memberDto.isJobSearch())
                        .company("취준")
                        .profileImage(memberDto.isExistProfileImage())
                        .companyBlind(true)
                        .build();
            }else{ //재직중일경우
                member = Member.builder()
                        .email(memberDto.getEmail())
                        .password(passwordEncoder.encode(memberDto.getPassword()))
                        .cohort(memberDto.getCohort())
                        .campus(memberDto.getCampus())
                        .jobSearch(memberDto.isJobSearch())
                        .company(memberDto.getCompany())
                        .profileImage(memberDto.isExistProfileImage())
                        .companyBlind(memberDto.getCompanyBlind())
                        .build();
            }

            if(!file.isEmpty()&&memberDto.isExistProfileImage()) {//프로필 이미지가 존재한다면
                minIoService.uploadFileToMinIOBySignUp(member.getId(),file);
            }

            //멤버 저장
            memberRepository.save(member);

        } catch(Exception e){
            throw new MemberCreationException(e.getMessage(),e);
        }
    }

    @Override
    public GetMemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return new GetMemberDto(member);
    }

    @Override
    public GetLoginDto getLoginInformation(CustomUserDetails userDetails) throws IOException {

        Long memberId = userDetails.getMember().getId();

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        String profileImage = minIoService.getFileToMinIO(userDetails);

        return new GetLoginDto(member,profileImage);
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
