package com.localsearch.search.presentation;

import com.localsearch.auth.Login;
import com.localsearch.auth.dto.LoginMember;
import com.localsearch.search.dto.request.CreateReviewRequest;
import com.localsearch.search.dto.response.LocalSearchResponse;
import com.localsearch.search.dto.response.PlaceReviewResponse;
import com.localsearch.search.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/search/place/{placeId}")
    public ResponseEntity<PlaceReviewResponse> searchPlaceReview(
            @PathVariable final String placeId
    ) {
        return ResponseEntity.ok().body(searchService.getPlaceReview(placeId));
    }

    @PostMapping("/search/review")
    public ResponseEntity<Void> createReview(
            @RequestBody @Valid final CreateReviewRequest createReviewRequest,
            @Login final LoginMember loginMember
    ) {
        searchService.createReview(loginMember.getMemberId(), createReviewRequest);
        return ResponseEntity.noContent().build();
    }
}
