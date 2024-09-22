package com.localsearch.search.service;

import com.localsearch.search.infrastructure.GoogleMapProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchService {

    private final GoogleMapProvider googleMapProvider;
    public void getLocalPlaces(double latitude, double longitude, String category, String rankPreference) {

        googleMapProvider.requestLocalSearch(latitude, longitude, category, rankPreference);
    }
}
