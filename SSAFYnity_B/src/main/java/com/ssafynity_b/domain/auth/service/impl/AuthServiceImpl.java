package com.ssafynity_b.domain.auth.service.impl;

import com.ssafynity_b.domain.auth.dto.LoginRequest;
import com.ssafynity_b.domain.auth.dto.LoginResponse;
import com.ssafynity_b.domain.auth.service.AuthService;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import com.ssafynity_b.global.jwt.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtUtil jwtUtil, MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        //클라이언트가 로그인을 시도하는 이메일을 Member Table에서 검색, 없으면 예외처리
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(MemberNotFoundException::new);
        String rawPassword = loginRequest.getPassword(); //클라이언트가 로그인을 시도하는 패스워드
        String encodedPassword = member.getPassword(); //회원가입 시 저장했던 패스워드(암호화되어있음)

        //ID,비밀번호 검증 로직
        if (passwordEncoder.matches(rawPassword,encodedPassword)) { //비밀번호가 일치하면 진입
            // 토큰 생성
            String jwtToken = jwtUtil.generateToken(String.valueOf(member.getId())); //MemberId를 토큰에 저장
            return LoginResponse.builder()
                    .jwtToken(jwtToken)
                    .build();
        }
        return null; // 비밀번호가 일치하지 않는다면 null 반환
    }
}
