package com.localsearch.search.repository;

import com.localsearch.search.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = "member")
    List<Review> findByPlacePlaceIdOrderByCreatedAtDesc(String placeId);
}
