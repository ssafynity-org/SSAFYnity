package com.ssafinity_b.domain.member.service;

import com.ssafinity_b.domain.member.dto.CreateMemberDto;
import com.ssafinity_b.domain.member.dto.GetMemberDto;
import com.ssafinity_b.domain.member.dto.UpdateMemberDto;

public interface MemberService {

    Long createMember(CreateMemberDto memberDto);

    GetMemberDto getMember(Long memberId);

    Long updateMember(UpdateMemberDto memberDto);

    void deleteMember(Long memberId);
}
