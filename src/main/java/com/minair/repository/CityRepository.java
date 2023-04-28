package com.minair.repository;

import com.minair.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Boolean existsByAirportCode(String airportCode);

    Optional<City> findByName(String name);
}
