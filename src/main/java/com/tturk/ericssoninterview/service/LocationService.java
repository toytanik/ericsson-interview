package com.tturk.ericssoninterview.service;

import com.tturk.ericssoninterview.assembler.LocationAssembler;
import com.tturk.ericssoninterview.assembler.PlaceAssembler;
import com.tturk.ericssoninterview.entity.Location;
import com.tturk.ericssoninterview.entity.Place;
import com.tturk.ericssoninterview.model.LocationResponse;
import com.tturk.ericssoninterview.repository.LocationRepository;
import com.tturk.ericssoninterview.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final PlaceRepository placeRepository;

    public void createLocation(LocationResponse newLocation) {
        Location location = LocationAssembler.toEntity(newLocation);
        locationRepository.save(location);

        List<Place> places = PlaceAssembler.toEntities(newLocation.getPlaceResponses(), location);
        placeRepository.saveAll(places);

    }

    public LocationResponse getLocation(Double longitude, Double latitude, Integer radius) {
        Optional<Location> location = locationRepository.findFirstByLatitudeAndLongitudeAndRadius(longitude, latitude, radius);
        return location.map(LocationAssembler::toModel).orElse(null);
    }
}
