package com.tturk.ericssoninterview.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String placeId;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
}
