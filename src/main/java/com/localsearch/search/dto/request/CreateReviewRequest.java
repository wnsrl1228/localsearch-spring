package com.localsearch.search.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateReviewRequest {

    @NotBlank(message = "잘못된 접근입니다.")
    private String placeId;

    @NotBlank(message = "리뷰를 입력해주세요.")
    private String content;

    private double rating;

    public CreateReviewRequest(String placeId, String content, double rating) {
        this.placeId = placeId;
        this.content = content;
        this.rating = rating;
    }
}