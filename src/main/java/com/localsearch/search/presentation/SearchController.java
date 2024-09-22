package com.localsearch.search.presentation;

import com.localsearch.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<Void> search(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("category") String category,
            @RequestParam(value = "sort", defaultValue = "POPULARITY") String rankPreference
    ) {
        searchService.getLocalPlaces(latitude, longitude, category, rankPreference);
        return ResponseEntity.ok().build();
    }
}
