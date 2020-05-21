package com.tturk.ericssoninterview.validator;

public class LocationValidator {
    public Double checkLatitude(Double latitude) {
        if (latitude != null) {
            return latitude;
        } else {
            throw new IllegalArgumentException(latitude + " latitude can not be empty :) ");
        }
    }

    public Double checkLongitude(Double longitude) {
        if (longitude != null) {
            return longitude;
        } else {
            throw new IllegalArgumentException(longitude + " longitude can not be empty :) ");
        }
    }

    public Integer checkRadius(Integer radius) {
        if (radius != null) {
            return radius;
        } else {
            throw new IllegalArgumentException(radius + " Radius can not be empty :) ");
        }
    }
}
