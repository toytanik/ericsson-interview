package com.tturk.ericssoninterview.repository;

import com.tturk.ericssoninterview.entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location,Long> {
    List<Location> findAll();

    Optional<Location> findFirstByLatitudeAndLongitudeAndRadius(Double longitude, Double latitude, Integer radius);
}
