package com.tturk.ericssoninterview.service;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.tturk.ericssoninterview.assembler.PlaceAssembler;
import com.tturk.ericssoninterview.model.LocationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlacesApiService {

    @Value("${google.api.key}")
    private String apiKey;

    public LocationResponse getNearbyPlaces(Double latitude, Double longitude, Integer radius)
            throws InterruptedException, ApiException, IOException {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setLatitude(latitude);
        locationResponse.setLongitude(longitude);
        locationResponse.setRadius(radius);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        LatLng latLng = new LatLng(latitude, longitude);

        NearbySearchRequest request = PlacesApi.nearbySearchQuery(context, latLng);
        request.radius(radius);
        PlacesSearchResponse response = request.await();
        List<PlacesSearchResult> searchResults = new ArrayList<>(Arrays.asList(response.results));

        while (!StringUtils.isEmpty(response.nextPageToken)) {
            request = PlacesApi.nearbySearchNextPage(context, response.nextPageToken);
            request.radius(radius);
            try {
                response = request.await();
            } catch (InvalidRequestException ignored) {
                log.warn("nextPageToken is not valid yet, trying again after 500ms");
                Thread.sleep(500);
            }
            searchResults.addAll(Arrays.asList(response.results));
        }
        locationResponse.setPlaceResponses(PlaceAssembler.toPlaceResponses(searchResults));
        return locationResponse;
    }
}
