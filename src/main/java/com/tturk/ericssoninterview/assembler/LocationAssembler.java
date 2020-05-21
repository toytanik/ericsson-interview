package com.tturk.ericssoninterview.assembler;

import com.tturk.ericssoninterview.entity.Location;
import com.tturk.ericssoninterview.model.LocationResponse;
import com.tturk.ericssoninterview.validator.LocationValidator;

public class LocationAssembler {

    public static Location toEntity(LocationResponse locationResponse) {
        Location location = new Location();
        LocationValidator locationValidator = new LocationValidator();
        location.setLatitude(locationValidator.checkLatitude(locationResponse.getLatitude())); //getSurname().toLowerCase()
        location.setLongitude(locationValidator.checkLongitude(locationResponse.getLongitude()));
        location.setRadius(locationValidator.checkRadius(locationResponse.getRadius()));

        return location;
    }

    public static LocationResponse toModel(Location location) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setLatitude(location.getLatitude());
        locationResponse.setLongitude(location.getLongitude());
        locationResponse.setRadius(location.getRadius());
        locationResponse.setPlaceResponses(PlaceAssembler.toModels(location.getPlaces()));
        return locationResponse;
    }

}
