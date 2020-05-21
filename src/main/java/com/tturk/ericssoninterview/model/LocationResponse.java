package com.tturk.ericssoninterview.model;

import lombok.Data;

import java.util.List;

@Data
public class LocationResponse {
    private Double longitude;
    private Double latitude;
    private Integer radius;
    private List<PlaceResponse> placeResponses;
}
