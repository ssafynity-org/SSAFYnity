package com.ssafinity_b.domain.member.service;

import com.ssafinity_b.domain.member.dto.*;

public interface MemberService {

    Long createMember(CreateMemberDto memberDto);

    GetMemberDto getMember(Long memberId);

    Long updateMember(UpdateMemberDto memberDto);

    void deleteMember(Long memberId);
}
