package com.tturk.ericssoninterview.model;

import lombok.Data;

@Data
public class LocationRequest {

    private Double longitude;
    private Double latitude;
    private Integer radius;

}
