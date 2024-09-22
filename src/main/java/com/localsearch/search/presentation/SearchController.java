package com.localsearch.search.presentation;

import com.localsearch.search.dto.response.LocalSearchResponse;
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
    public ResponseEntity<LocalSearchResponse> search(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "radius", defaultValue = "500") double radius,
            @RequestParam(value = "category", defaultValue = "food") String category,
            @RequestParam(value = "sort", defaultValue = "POPULARITY") String rankPreference
    ) {
        return ResponseEntity.ok().body(searchService.getLocalPlaces(latitude, longitude, radius, category, rankPreference));
    }
}
