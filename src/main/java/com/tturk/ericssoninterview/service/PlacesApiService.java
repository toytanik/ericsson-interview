package com.tturk.ericssoninterview.service;

import com.tturk.ericssoninterview.assembler.PlaceAssembler;
import com.tturk.ericssoninterview.model.LocationResponse;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        LatLng latLng = new LatLng(latitude, longitude);

        NearbySearchRequest request = PlacesApi.nearbySearchQuery(context, latLng);
        request.radius(radius);
        PlacesSearchResponse response = request.await();
        List<PlacesSearchResult> searchResults = new ArrayList<>(Arrays.asList(response.results));

        while (!StringUtils.isEmpty(response.nextPageToken)) {
            Thread.sleep(1000);
            request = PlacesApi.nearbySearchNextPage(context, response.nextPageToken);
            request.radius(radius);
            response = request.await();
            searchResults.addAll(Arrays.asList(response.results));
        }
        locationResponse.setPlaceResponses(PlaceAssembler.toPlaceResponses(searchResults));
        return locationResponse;
    }
}
