package com.localsearch.search.dto.response;

import com.localsearch.search.dto.LocalSearchAPIResponse;
import com.localsearch.search.dto.PlaceElement;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LocalSearchResponse {

    private List<PlaceElement> places;

    public static LocalSearchResponse ofPlaces(List<LocalSearchAPIResponse.Place> places) {
        final List<PlaceElement> placeElements = places.stream()
                .map(PlaceElement::of)
                .toList();

        return LocalSearchResponse.builder()
                .places(placeElements)
                .build();
    }
}
