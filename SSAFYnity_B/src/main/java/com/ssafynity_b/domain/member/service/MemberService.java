package com.ssafynity_b.domain.member.service;

import com.ssafynity_b.domain.member.dto.*;

import java.util.List;

public interface MemberService {

    Long createMember(CreateMemberDto memberDto);

    GetMemberDto getMember(Long memberId);

    Long updateMember(UpdateMemberDto memberDto);

    void deleteMember(Long memberId);

    List<GetMemberDto> getMemberByCompany(String keyword);
}
