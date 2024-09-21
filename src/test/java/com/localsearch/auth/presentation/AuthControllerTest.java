package com.localsearch.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localsearch.auth.dto.LoginTokens;
import com.localsearch.auth.dto.request.LoginRequest;
import com.localsearch.auth.dto.request.SignUpRequest;
import com.localsearch.auth.infrastructure.JwtProvider;
import com.localsearch.auth.service.AuthService;
import com.localsearch.global.config.WebConfig;
import com.localsearch.global.exception.AuthException;
import com.localsearch.global.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {AuthController.class, WebConfig.class, JwtProvider.class})
class AuthControllerTest {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    @MockBean
    private AuthService authService;
    @MockBean
    private JwtProvider jwtProvider;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인에 성공한다.")
    void login_success() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("userid", "password");
        LoginTokens loginTokens = new LoginTokens(ACCESS_TOKEN, REFRESH_TOKEN);
        given(authService.login(any())).willReturn(loginTokens);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value(REFRESH_TOKEN));
    }

    @Test
    @DisplayName("아이디가 한 글자인 경우 예외가 발생한다.")
    void login_fail_1() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("1", "password");
        LoginTokens loginTokens = new LoginTokens(ACCESS_TOKEN, REFRESH_TOKEN);
        given(authService.login(any())).willReturn(loginTokens);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("아이디는 5~30자까지 입력할 수 있습니다."));
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void signUp_success() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("userid", "password", "nickname");
        LoginTokens loginTokens = new LoginTokens(ACCESS_TOKEN, REFRESH_TOKEN);
        given(authService.signUp(any())).willReturn(loginTokens);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value(REFRESH_TOKEN));
    }

    @Test
    @DisplayName("토큰 갱신에 성공한다.")
    void refreshAccessToken_success() throws Exception {
        // given
        given(authService.renewAccessToken(any())).willReturn(ACCESS_TOKEN);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/token")
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", REFRESH_TOKEN)))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(ACCESS_TOKEN));
    }

    @Test
    @DisplayName("토큰 갱신시 유효하지 않은 리프레쉬 토큰일 경우 예외가 발생한다.")
    void refreshAccessToken_fail_INVALID_TOKEN() throws Exception {
        // given
        willThrow(new AuthException(ErrorCode.INVALID_TOKEN)).given(authService).renewAccessToken(any());

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/token")
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", REFRESH_TOKEN)))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.INVALID_TOKEN.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorCode.INVALID_TOKEN.getMessage()));
    }
}