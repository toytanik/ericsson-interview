package com.tturk.ericssoninterview.assembler;

import com.tturk.ericssoninterview.entity.Location;
import com.tturk.ericssoninterview.entity.Place;
import com.tturk.ericssoninterview.model.PlaceResponse;
import com.google.maps.model.PlacesSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PlaceAssembler {

    public static List<PlaceResponse> toPlaceResponses(List<PlacesSearchResult> searchResults) {
        if (searchResults == null) {
            return null;
        }
        List<PlaceResponse> placeResponses = new ArrayList<>();

        for (PlacesSearchResult searchResult : searchResults) {
            placeResponses.add(toModel(searchResult));
        }

        return placeResponses;

    }

    private static PlaceResponse toModel(PlacesSearchResult searchResult) {
        PlaceResponse placeResponse = new PlaceResponse();
        placeResponse.setName(searchResult.name);
        placeResponse.setPlaceId(searchResult.placeId);
        return placeResponse;
    }

    public static List<Place> toEntities(List<PlaceResponse> placeResponses, Location location) {
        if (placeResponses == null) {
            return null;
        }
        List<Place> entities = new ArrayList<>();
        for (PlaceResponse placeResponse : placeResponses) {
            entities.add(toEntity(placeResponse, location));
        }
        return entities;
    }

    private static Place toEntity(PlaceResponse placeResponse, Location location) {
        Place entity = new Place();
        entity.setLocation(location);
        entity.setName(placeResponse.getName());
        entity.setPlaceId(placeResponse.getPlaceId());
        return entity;
    }

    static List<PlaceResponse> toModels(List<Place> entities) {
        if (entities == null) {
            return null;
        }
        List<PlaceResponse> models = new ArrayList<>();
        for (Place entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    private static PlaceResponse toModel(Place entity) {
        PlaceResponse model = new PlaceResponse();
        model.setName(entity.getName());
        model.setPlaceId(entity.getPlaceId());
        return model;
    }
}
