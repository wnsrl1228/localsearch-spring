package com.localsearch.search.service;

import com.localsearch.search.dto.LocalSearchAPIResponse;
import com.localsearch.search.dto.response.LocalSearchResponse;
import com.localsearch.search.infrastructure.GoogleMapProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchService {

    private final GoogleMapProvider googleMapProvider;
    public LocalSearchResponse getLocalPlaces(double latitude, double longitude, String category, String rankPreference) {

        LocalSearchAPIResponse localSearchAPIResponse
                = googleMapProvider.requestLocalSearch(latitude, longitude, category, rankPreference);

        return LocalSearchResponse.ofPlaces(localSearchAPIResponse.getPlaces());
    }
}
