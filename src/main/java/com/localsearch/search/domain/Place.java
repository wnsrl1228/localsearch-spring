package com.localsearch.search.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placeId;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private int reviewCount;

    public Place(String placeId, double rating, int reviewCount) {
        this.placeId = placeId;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
    public Place(String placeId) {
        this(placeId, 0.0, 0);
    }

    public void updateRatingAndReviewCount(double rating) {
        this.rating = Math.floor((this.rating * reviewCount + rating) / (reviewCount + 1) * 10) / 10;
        this.reviewCount += 1;
    }
}
