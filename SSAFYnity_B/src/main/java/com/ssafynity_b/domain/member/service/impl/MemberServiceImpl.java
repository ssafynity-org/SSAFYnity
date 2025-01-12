package com.ssafynity_b.domain.member.service.impl;

import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.dto.*;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberDocumentRepository;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.domain.member.service.MemberService;
import com.ssafynity_b.global.exception.LoginFailedException;
import com.ssafynity_b.global.exception.MemberCreationException;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import com.ssafynity_b.global.fileupload.minio.service.MinIoUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.checkerframework.checker.units.qual.min;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final MinIoUploadService minIoUploadService;

    @Transactional
    @Override
    public Long createMember(CreateMemberDto memberDto) {
        try {
            Member member = new Member(memberDto.getEmail(), passwordEncoder.encode(memberDto.getPassword()), memberDto.getName(), memberDto.getCompany(), "ROLE_MEMBER");
            Member savedMember = memberRepository.save(member);
            MemberDocument memberDocument = new MemberDocument(savedMember.getId(), savedMember.getEmail(), savedMember.getPassword(), savedMember.getName(), savedMember.getCompany(), savedMember.getRole());
            documentRepository.save(memberDocument);
            return savedMember.getId();
        } catch(Exception e){
            throw new MemberCreationException("회원 생성 실패",e);
        }
    }

    //일반 회원가입시에 사용
    @Transactional
    @Override
    public void createMemberAndProfileImage(CreateMemberDto memberDto, MultipartFile file) throws FileUploadException {
        try{
            Long memberId = createMember(memberDto);
            minIoUploadService.uploadFileToMinIoBySignUp(memberId,file);
        }catch(MemberCreationException e){
            //회원 생성 실패 처리
            throw e;
        }catch(FileUploadException e){
            //파일 저장 실패 처리
            throw e;
        }
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
