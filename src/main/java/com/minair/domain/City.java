package com.minair.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    private String country;
    private String name;
    private String airportCode;
    private double longitude;
    private double latitude;
    private int cluster;

    @Builder
    public City(String country, String name, String airportCode,
                double longitude, double latitude, int cluster) {
        this.country = country;
        this.name = name;
        this.airportCode = airportCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cluster = cluster;
    }

    public static City of(Location location) {
        return City.builder()
                .country(location.getCountry())
                .name(location.getCityName())
                .airportCode(location.getAirportCode())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .cluster(location.getCluster())
                .build();
    }

}
