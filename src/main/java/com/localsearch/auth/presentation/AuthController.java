package com.localsearch.auth.presentation;

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
    public ResponseEntity<Boolean> login(
            @RequestBody @Valid final LoginRequest loginRequest
    ) {

        boolean result = authService.login(loginRequest);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(
            @RequestBody @Valid final SignUpRequest signUpRequest
    ) {

        boolean result = authService.signUp(signUpRequest);
        return ResponseEntity.ok().body(result);
    }
}
