package com.ssafynity_b.global.jwt;

import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Swagger 관련 경로, 회원가입,로그인 경로는 필터링하지 않음
        if (requestPath.startsWith("/swagger-ui") || requestPath.startsWith("/v3/api-docs") || requestPath.startsWith("/api/auth/login") || requestPath.startsWith("/api/member") && "POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response); // 다음 필터로 이동
            return;
        }

        try{

            String token = parseBearerToken(request);
            if(token==null){
                return;
            }

            String memberId = jwtProvider.extractMemberId(token);
            if(memberId==null){
                return;
            }

            System.out.println("jwt토큰 검증 성공 : " + token);
            System.out.println("memberId : " + memberId);

            Member member = memberRepository.findById(Long.parseLong(memberId)).orElseThrow(MemberNotFoundException::new);
            String role = member.getRole(); //role : ROLE_USER, ROLE_ADMIN

            System.out.println("role : " + role);

            //권한 정보 설정
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            //권한정보를 담을 비어있는 SecurityContext를 생성
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            CustomUserDetails userDetails = new CustomUserDetails(member, authorities);

            //권한정보를 담은 토큰을 발행
            AbstractAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //SecurityContext에 권한정보 토큰을 담아줌
            securityContext.setAuthentication(authenticationToken);

            //SecurityContextHolder에 권한정보 토큰을 담은 SecurityContext를 저장
            SecurityContextHolder.setContext(securityContext);

        }catch(Exception e){
            e.printStackTrace();
        }


        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    //request객체로부터 토큰값을 가져오는 메소드
    private String parseBearerToken(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        //Authorization Header에 실제로 값이 존재하는지 판단
        boolean hasAuthorization = StringUtils.hasText(authorization);

        //Authorization이 존재하지않으면 null반환
        if(!hasAuthorization)
            return null;

        //Authorization이 'Bearer '로 시작하지않는다면 null반환
        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer)
            return null;

        //토큰값 반환
        String token = authorization.substring(7);
        return token;
    }
}
