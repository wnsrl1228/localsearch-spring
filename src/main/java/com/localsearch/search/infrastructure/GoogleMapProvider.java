package com.localsearch.search.infrastructure;

import com.localsearch.global.exception.ErrorCode;
import com.localsearch.global.exception.InvalidException;
import com.localsearch.search.dto.LocalSearchAPIRequest;
import com.localsearch.search.dto.LocalSearchAPIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.*;

@Component
public class GoogleMapProvider {

    private static final String PROPERTIES_PATH = "${google-map.provider.";
    private final RestTemplate restTemplate;
    protected final String apiURL;
    protected final String apiSecretKey;

    private final Map<String, List<String>> categoryMap = new HashMap<>();;
    private final List<String> medicalList = Arrays.asList("dental_clinic", "dentist", "doctor", "drugstore", "hospital", "medical_lab", "pharmacy", "physiotherapist", "spa");
    private final List<String> cafeBakeryList = Arrays.asList("cafe", "coffee_shop", "baker");
    private final List<String> foodList = Arrays.asList("restaurant", "sandwich_shop", "steak_house", "ice_cream_shop", "bar");
    private final List<String> accommodationList =  Arrays.asList("bed_and_breakfast", "campground", "camping_cabin", "cottage", "extended_stay_hotel", "farmstay", "guest_house", "hostel", "hotel", "lodging", "motel", "private_guest_room", "resort_hotel", "rv_park");
    private final List<String> storeList =  Arrays.asList("store");

    public GoogleMapProvider(
            @Value(PROPERTIES_PATH + "api-url}") final String apiURL,
            @Value(PROPERTIES_PATH + "api-secret-key}") final String apiSecretKey,
            final RestTemplate restTemplate
    ) {
        this.apiSecretKey = apiSecretKey;
        this.apiURL = apiURL;
        this.restTemplate = restTemplate;

        categoryMap.put("medical", medicalList);              // 의료
        categoryMap.put("cafeBakery", cafeBakeryList);        // 카페/빵
        categoryMap.put("food", foodList);                    // 음식
        categoryMap.put("accommodation", accommodationList);  // 숙박
        categoryMap.put("store", storeList);                  // 상점
    }

    public LocalSearchAPIResponse requestLocalSearch(double latitude, double longitude, String category, String rankPreference) {

        // Request Body
        List<String> includedTypes = getIncludedTypes(category);
        LocalSearchAPIRequest requestBody = new LocalSearchAPIRequest(
                includedTypes,
                20,
                rankPreference,
                latitude, longitude, 500
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", apiSecretKey);  // API Key 설정
        headers.set("X-Goog-FieldMask", "places.displayName,places.id,places.primaryTypeDisplayName,places.location,places.primaryType,places.formattedAddress,places.attributions,places.googleMapsUri");

        HttpEntity<LocalSearchAPIRequest> entity = new HttpEntity<>(requestBody, headers);

        // api 요청
        try {
            final ResponseEntity<LocalSearchAPIResponse> localSearchAPIResponse = restTemplate.exchange(
                    apiURL,
                    HttpMethod.POST,
                    entity,
                    LocalSearchAPIResponse.class
            );

            return Optional.ofNullable(localSearchAPIResponse.getBody())
                    .orElseThrow(() -> new InvalidException(ErrorCode.INVALID_SEARCH_REQUEST));

        } catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
            throw new RuntimeException("서버에 문제가 발생하였습니다.");
        }
    }

    private List<String> getIncludedTypes(String category) {

        if (category.equals("all")) {
            return null;
        }

        List<String> includedTypes = categoryMap.getOrDefault(category, null);
        if (includedTypes == null) {
            throw new InvalidException(ErrorCode.INVALID_SEARCH_REQUEST);
        }
        return includedTypes;
    }
}
