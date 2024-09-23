package com.localsearch.search.service;

import com.localsearch.search.domain.Place;
import com.localsearch.search.domain.Review;
import com.localsearch.search.dto.LocalSearchAPIResponse;
import com.localsearch.search.dto.response.LocalSearchResponse;
import com.localsearch.search.dto.response.PlaceReviewResponse;
import com.localsearch.search.infrastructure.GoogleMapProvider;
import com.localsearch.search.repository.PlaceRepository;
import com.localsearch.search.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchService {

    private final GoogleMapProvider googleMapProvider;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public LocalSearchResponse getLocalPlaces(double latitude, double longitude, double radius, String category, String rankPreference) {

        LocalSearchAPIResponse localSearchAPIResponse
                = googleMapProvider.requestLocalSearch(latitude, longitude, radius, category, rankPreference);

        return LocalSearchResponse.ofPlaces(localSearchAPIResponse.getPlaces());
    }

    public PlaceReviewResponse getPlaceReview(String placeId) {
        // 1. 장소 db에서 장소 정보 있는지 확인
        Place place = placeRepository.findByPlaceId(placeId)
                .orElse(null);
        // - 없을 경우 : 리뷰도 없다는 것이니 끝
        if (place == null) {
            return PlaceReviewResponse.of(placeId);
        }
        // - 있을 경우 : 장소 데이터와 리뷰 데이터 반환
        List<Review> reviews = reviewRepository.findByPlacePlaceId(placeId);

        return PlaceReviewResponse.of(place, reviews);
    }

    /**
     * 리뷰 작성시
     * 1. 장소가 등록 안 되어있으면 db에 추가
     * 2. 리뷰 추가
     * 3. 장소에서 평점, 댓글개수 갱신
     */
}
