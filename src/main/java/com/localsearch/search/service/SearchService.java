package com.localsearch.search.service;

import com.localsearch.global.exception.ErrorCode;
import com.localsearch.global.exception.InvalidException;
import com.localsearch.member.domain.Member;
import com.localsearch.member.repository.MemberRepository;
import com.localsearch.search.domain.Place;
import com.localsearch.search.domain.Review;
import com.localsearch.search.dto.LocalSearchAPIResponse;
import com.localsearch.search.dto.request.CreateReviewRequest;
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
    private final MemberRepository memberRepository;

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

    public void createReview(Long memberId, CreateReviewRequest createReviewRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidException(ErrorCode.NOT_FOUND_MEMBER_ID));

        // 1. 장소가 등록 안 되어있으면 db에 추가
        Place place = placeRepository.findByPlaceId(createReviewRequest.getPlaceId())
                .orElse(null);

        if (place == null) {
            place = placeRepository.save(new Place(createReviewRequest.getPlaceId()));
        }

        // 2. 장소의 평점개수, 평균 평점 수정
        place.updateRatingAndReviewCount(createReviewRequest.getRating());

        // 3. 리뷰 등록
        Review review = new Review(member, place, createReviewRequest.getContent(), createReviewRequest.getRating());
        reviewRepository.save(review);
    }
}
