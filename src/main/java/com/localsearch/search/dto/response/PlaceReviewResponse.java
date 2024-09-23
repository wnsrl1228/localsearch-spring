package com.localsearch.search.dto.response;

import com.localsearch.search.domain.Place;
import com.localsearch.search.domain.Review;
import com.localsearch.search.dto.LocalSearchAPIResponse;
import com.localsearch.search.dto.PlaceElement;
import com.localsearch.search.dto.ReviewElement;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceReviewResponse {

    private String placeId;
    private double rating;
    private int reviewCount;
    private Boolean isReviewEmpty;
    private List<ReviewElement> reviews;

    public static PlaceReviewResponse of(Place place, List<Review> reviews) {
        List<ReviewElement> reviewElements = null;
        if (reviews != null) {
            reviewElements = reviews.stream()
                    .map(ReviewElement::of)
                    .toList();
        }
        return PlaceReviewResponse.builder()
                .placeId(place.getPlaceId())
                .rating(place.getRating())
                .reviewCount(place.getReviewCount())
                .isReviewEmpty(reviews == null)
                .reviews(reviewElements)
                .build();
    }

    public static PlaceReviewResponse of(String placeId) {
        return PlaceReviewResponse.builder()
                .placeId(placeId)
                .rating(0.0)
                .reviewCount(0)
                .isReviewEmpty(true)
                .reviews(null)
                .build();
    }

}
