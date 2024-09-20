package com.localsearch.auth.service;

import com.localsearch.auth.dto.request.LoginRequest;
import com.localsearch.auth.dto.request.SignUpRequest;
import com.localsearch.global.exception.AuthException;
import com.localsearch.global.exception.ErrorCode;
import com.localsearch.member.domain.Member;
import com.localsearch.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql({"/h2-truncate.sql"})
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String REGISTERED_USERID = "registeredUser123";
    private static final String REGISTERED_PASSWORD = "registeredPassword123!";
    private static final String REGISTERED_NICKNAME = "registeredNickname";
    private static final String UNREGISTERED_USERID = "unregisteredUser456";
    private static final String UNREGISTERED_PASSWORD = "unregisteredPassword456!";
    private static final String UNREGISTERED_NICKNAME = "unregisteredNickname";



    private Member initMember;

    @BeforeEach
    void init() {
        initMember = memberRepository.save(new Member(REGISTERED_USERID, passwordEncoder.encode(REGISTERED_PASSWORD), REGISTERED_NICKNAME));
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    void login_success() {
        // given
        LoginRequest loginRequest = new LoginRequest(REGISTERED_USERID, REGISTERED_PASSWORD);

        // when
        boolean result = authService.login(loginRequest);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아이디가 일치하지 않아 로그인에 실패한다.")
    void login_fail_1() {
        // given
        LoginRequest loginRequest = new LoginRequest("fail-userId", REGISTERED_PASSWORD);

        // when then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage(ErrorCode.FAILED_TO_LOGIN.getMessage());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않아 로그인에 실패한다.")
    void login_fail_2() {
        // given
        LoginRequest loginRequest = new LoginRequest(REGISTERED_USERID, "fail-password");

        // when then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage(ErrorCode.FAILED_TO_LOGIN.getMessage());
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void signUp_success() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(UNREGISTERED_USERID, UNREGISTERED_PASSWORD, UNREGISTERED_NICKNAME);

        // when
        boolean result = authService.signUp(signUpRequest);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유저아이디가 일치하여 회원가입에 실패한다.")
    void signUp_fail_1() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(REGISTERED_USERID, UNREGISTERED_PASSWORD, UNREGISTERED_NICKNAME);

        // when then
        assertThatThrownBy(() -> authService.signUp(signUpRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage(ErrorCode.DUPLICATE_USERID.getMessage());
    }

    @Test
    @DisplayName("닉네임이 일치하여 회원가입에 실패한다.")
    void signUp_fail_2() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(UNREGISTERED_USERID, UNREGISTERED_PASSWORD, REGISTERED_NICKNAME);

        // when then
        assertThatThrownBy(() -> authService.signUp(signUpRequest))
                .isInstanceOf(AuthException.class)
                .hasMessage(ErrorCode.DUPLICATE_NICKNAME.getMessage());
    }
}