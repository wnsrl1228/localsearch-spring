package com.localsearch.auth.presentation;

import com.localsearch.auth.dto.LoginTokens;
import com.localsearch.auth.dto.request.LoginRequest;
import com.localsearch.auth.dto.request.SignUpRequest;
import com.localsearch.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginTokens> login(
            @RequestBody @Valid final LoginRequest loginRequest
    ) {
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<LoginTokens> signUp(
            @RequestBody @Valid final SignUpRequest signUpRequest
    ) {
        return ResponseEntity.ok().body(authService.signUp(signUpRequest));
    }
}
