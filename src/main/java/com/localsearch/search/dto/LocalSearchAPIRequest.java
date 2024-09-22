package com.localsearch.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocalSearchAPIRequest {
    private List<String> includedTypes;
    private int maxResultCount;
    private String rankPreference;
    private LocationRestriction locationRestriction;
    private String languageCode;
    public LocalSearchAPIRequest(List<String> includedTypes, int maxResultCount, String rankPreference, double latitude, double longitude, double radius) {
        this.includedTypes = includedTypes;
        this.maxResultCount = maxResultCount;
        this.rankPreference = rankPreference;
        this.locationRestriction = new LocationRestriction(latitude, longitude, radius);
        this.languageCode = "ko";

    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class LocationRestriction {
        private Circle circle;

        public LocationRestriction(double latitude, double longitude, double radius) {
            this.circle = new Circle(latitude, longitude, radius);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Circle {
        private Center center;
        private double radius;

        public Circle(double latitude, double longitude, double radius) {
            this.center = new Center(latitude, longitude);
            this.radius = radius;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Center {
        private double latitude;
        private double longitude;

        public Center(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
