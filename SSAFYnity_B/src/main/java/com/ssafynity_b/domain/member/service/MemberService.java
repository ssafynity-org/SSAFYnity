package com.ssafynity_b.domain.member.service;

import com.ssafynity_b.domain.member.dto.*;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    Long createMember(CreateMemberDto memberDto);

    GetMemberDto getMember(Long memberId);

    List<GetMemberDto> getAllMember();

    Long updateMember(UpdateMemberDto memberDto);

    void deleteMember(Long memberId);

    List<GetMemberDto> searchMemberByCompany(String keyword) throws IOException;

    List<GetMemberDto> searchMemberByName(String keyword);
}
