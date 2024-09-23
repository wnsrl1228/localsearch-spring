package com.localsearch.search.dto;

import com.localsearch.search.domain.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewElement {

    private String nickname;

    private String content;

    private double rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ReviewElement of(Review review) {
        return ReviewElement.builder()
                .nickname(review.getMember().getNickname())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
