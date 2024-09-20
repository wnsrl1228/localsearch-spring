package com.localsearch.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    FAILED_TO_LOGIN(1000, "아이디와 패스워드가 일치하지 않습니다."),
    DUPLICATE_USERID(1010, "이미 존재하는 아이디입니다."),
    DUPLICATE_NICKNAME(1011, "이미 존재하는 닉네임입니다.");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
