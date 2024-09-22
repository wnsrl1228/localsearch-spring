package com.localsearch.search.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class PlaceElement {
    private String id;
    private String displayName;
    private String primaryTypeDisplayName;
    private String formattedAddress;
    private String googleMapsUri;
    private double latitude;
    private double longitude;;

    public static PlaceElement of(LocalSearchAPIResponse.Place apiPlace) {
        return PlaceElement.builder()
                .id(apiPlace.getId())
                .displayName(apiPlace.getDisplayName().getText())
                .primaryTypeDisplayName(Optional.ofNullable(apiPlace.getPrimaryTypeDisplayName())
                        .map(LocalSearchAPIResponse.PrimaryTypeDisplayName::getText)
                        .orElse(""))
                .formattedAddress(Optional.ofNullable(apiPlace.getFormattedAddress()).orElse(""))
                .googleMapsUri(Optional.ofNullable(apiPlace.getGoogleMapsUri()).orElse(""))
                .latitude(apiPlace.getLocation().getLatitude())
                .longitude(apiPlace.getLocation().getLongitude())
                .build();
    }
}