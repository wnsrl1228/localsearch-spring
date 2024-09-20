package com.localsearch.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    @Length(min = 5, max = 30, message = "아이디는 5~30자까지 입력할 수 있습니다.")
    @NotBlank(message = "아이디을 입력해주세요.")
    private String userId;

    @Length(min = 5, max = 30, message = "비밀번호는 5~30자까지 입력할 수 있습니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Length(min = 2, max = 20, message = "닉네임은 2~20자까지 입력할 수 있습니다.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    public SignUpRequest(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
}