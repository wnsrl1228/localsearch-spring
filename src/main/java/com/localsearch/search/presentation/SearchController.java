package com.localsearch.search.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class SearchController {

    @GetMapping("/search")
    public ResponseEntity<Void> search() {
        return ResponseEntity.ok().build();
    }
}
