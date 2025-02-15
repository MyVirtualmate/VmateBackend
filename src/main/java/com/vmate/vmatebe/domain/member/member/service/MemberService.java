package com.vmate.vmatebe.domain.member.member.service;

import com.vmate.vmatebe.domain.member.member.entity.Member;
import com.vmate.vmatebe.domain.member.member.repository.MemberRepository;
import com.vmate.vmatebe.global.exceptions.GlobalException;
import com.vmate.vmatebe.global.rsData.RsData;
import com.vmate.vmatebe.global.security.SecurityUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    @Transactional
    public RsData<Member> join(String userEmail, String password) {
        if (findByUserEmail(userEmail).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .refreshToken(authTokenService.genRefreshToken())
                .build();

        memberRepository.save(member);
        return RsData.of("200", "%s님 환영합니다. 회원가입이 완료되었습니다. 로그인 후 이용해주세요.".formatted(member.getUserEmail()), member);
    }

    public Optional<Member> findByUserEmail(String userEmail) {
        return memberRepository.findByUserEmail(userEmail);
    }
    
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
    
    public Member getMemberByUsername(String username) {
        return findByUserEmail(username).orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));
    }

    public void updateIsStreamer(Long memberId, Boolean isStreamer) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        Member updatedMember = member.toBuilder()
                .isStreamer(isStreamer)
                .build();

        memberRepository.save(updatedMember);
    }

    public RsData<Member> whenSocialLogin(String providerTypeCode, String username, String nickname, String profileImgUrl) {
        Optional<Member> opMember = findByUserEmail(username);
        if (opMember.isPresent()) return RsData.of("200", "이미 존재합니다.", opMember.get());
        return join(username, "");
    }

    @AllArgsConstructor
    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private Member member;
        private String accessToken;
        private String refreshToken;
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String userEmail, String password) {
        Member member = findByUserEmail(userEmail)
                .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));
        if (!passwordMatches(member, password))
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");
        String refreshToken = member.getRefreshToken();
        String accessToken = authTokenService.genAccessToken(member);
        return RsData.of(
                "200-1",
                "로그인 성공",
                new AuthAndMakeTokensResponseBody(member, accessToken, refreshToken)
        );
    }

    @Transactional
    public String genAccessToken(Member member) {
        return authTokenService.genAccessToken(member);
    }

    public boolean passwordMatches(Member member, String password) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = authTokenService.getDataFrom(accessToken);
        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<String> authorities = (List<String>) payloadBody.get("authorities");
        return new SecurityUser(
                id,
                username,
                "",
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    public boolean validateToken(String token) {
        return authTokenService.validateToken(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 리프레시 토큰입니다."));
        String accessToken = authTokenService.genAccessToken(member);
        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }
}