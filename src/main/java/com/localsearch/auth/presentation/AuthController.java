package com.localsearch.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<Void> login() {

        return ResponseEntity.ok().body(null);
    }
}
