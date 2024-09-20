package com.localsearch.auth.service;

import com.localsearch.auth.domain.RefreshToken;
import com.localsearch.auth.dto.LoginTokens;
import com.localsearch.auth.dto.request.LoginRequest;
import com.localsearch.auth.dto.request.SignUpRequest;
import com.localsearch.auth.infrastructure.JwtProvider;
import com.localsearch.auth.repository.RefreshTokenRepository;
import com.localsearch.global.exception.AuthException;
import com.localsearch.global.exception.ErrorCode;
import com.localsearch.member.domain.Member;
import com.localsearch.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginTokens login(final LoginRequest loginRequest) {
        // 1. 아이디 일치 여부
        final Member member = memberRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new AuthException(ErrorCode.FAILED_TO_LOGIN));
        
        // 2. 패스워드 일치 여부
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new AuthException(ErrorCode.FAILED_TO_LOGIN);
        }

        // 3. 토큰 생성
        return generateTokensByMemberId(member.getId());
    }

    public LoginTokens signUp(final SignUpRequest signUpRequest) {
        // 1. 아이디 중복 여부
        if (memberRepository.existsByUserId(signUpRequest.getUserId())) {
            throw new AuthException(ErrorCode.DUPLICATE_USERID);
        }
        // 2. 닉네임 중복 여부
        if (memberRepository.existsByNickname(signUpRequest.getNickname())) {
            throw new AuthException(ErrorCode.DUPLICATE_NICKNAME);
        }
        // 3. 비밀번호 암호화
        final String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());

        final Member member = memberRepository.save(new Member(
                signUpRequest.getUserId(),
                encodePassword,
                signUpRequest.getNickname()
        ));
        // 4. 토큰 생성
        return generateTokensByMemberId(member.getId());
    }

    private LoginTokens generateTokensByMemberId(final Long memberId) {
        final LoginTokens loginTokens = jwtProvider.createLoginTokens(memberId.toString());

        refreshTokenRepository.findById(memberId)
                .ifPresentOrElse(
                        refreshToken -> refreshToken.updateToken(loginTokens.getRefreshToken()),
                        () -> refreshTokenRepository.save(new RefreshToken(memberId, loginTokens.getRefreshToken()))
                );
        return loginTokens;
    }
}
