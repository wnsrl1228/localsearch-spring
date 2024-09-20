package com.localsearch.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PasswordEncoderConfigTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void pwdEnc() {

        String basicPwd = "123451234512345123451234512345";

        String pwd = basicPwd;
        String encodedPwd = passwordEncoder.encode(pwd); //암호화 하는부분
        System.out.println(encodedPwd);
        assertThat(passwordEncoder.matches(pwd, encodedPwd)).isTrue();

        String pwd2 = basicPwd;
        String encodedPwd2 = passwordEncoder.encode(pwd2); //암호화 하는부분
        System.out.println(encodedPwd2);
        assertThat(passwordEncoder.matches(pwd2, encodedPwd2)).isTrue();

        // 동일한 비밀번호라도 다르게 암호화 된다.
        assertThat(encodedPwd).isNotEqualTo(encodedPwd2);
    }
}