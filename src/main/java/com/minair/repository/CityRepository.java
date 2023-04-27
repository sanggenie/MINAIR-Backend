package com.minair.repository;

import com.minair.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    Boolean existsByAirportCode(String airportCode);
}
