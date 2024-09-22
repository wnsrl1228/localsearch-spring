package com.localsearch.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor()
@AllArgsConstructor
public class LocalSearchAPIResponse {
    private List<Place> places;

    @Getter
    @NoArgsConstructor()
    @AllArgsConstructor
    public static class Place {
        private String id;
        private String formattedAddress;
        private Location location;
        private String googleMapsUri;
        private DisplayName displayName;
        private PrimaryTypeDisplayName primaryTypeDisplayName;
        private String primaryType;

        @Override
        public String toString() {
            return "Place{" +
                    "id='" + id + '\'' +
                    ", formattedAddress='" + formattedAddress + '\'' +
                    ", location=" + location +
                    ", googleMapsUri='" + googleMapsUri + '\'' +
                    ", displayName=" + displayName +
                    ", primaryTypeDisplayName=" + primaryTypeDisplayName +
                    ", primaryType='" + primaryType + '\'' +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor()
    @AllArgsConstructor
    public static class Location {
        private double latitude;
        private double longitude;

        @Override
        public String toString() {
            return "Location{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor()
    @AllArgsConstructor
    public static class DisplayName {
        private String text;
        private String languageCode;

        @Override
        public String toString() {
            return "DisplayName{" +
                    "text='" + text + '\'' +
                    ", languageCode='" + languageCode + '\'' +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor()
    @AllArgsConstructor
    public static class PrimaryTypeDisplayName {
        private String text;
        private String languageCode;

        @Override
        public String toString() {
            return "PrimaryTypeDisplayName{" +
                    "text='" + text + '\'' +
                    ", languageCode='" + languageCode + '\'' +
                    '}';
        }
    }
}