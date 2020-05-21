package com.tturk.ericssoninterview.controller;

import com.tturk.ericssoninterview.model.LocationRequest;
import com.tturk.ericssoninterview.model.LocationResponse;
import com.tturk.ericssoninterview.service.LocationService;
import com.tturk.ericssoninterview.service.PlacesApiService;
import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final PlacesApiService placesApiService;

    @PostMapping
    public LocationResponse createLocation(@RequestBody LocationRequest locReqest)
            throws InterruptedException, ApiException, IOException {
        LocationResponse existingLocation = locationService.getLocation(
                locReqest.getLatitude(),
                locReqest.getLongitude(),
                locReqest.getRadius());
        if (existingLocation != null) {
            return existingLocation;
        }

        LocationResponse newLocation = placesApiService.getNearbyPlaces(
                locReqest.getLatitude(),
                locReqest.getLongitude(),
                locReqest.getRadius()
        );

        locationService.createLocation(newLocation);

        return newLocation;
    }

}
