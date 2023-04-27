package com.minair.service;

import com.minair.domain.City;
import com.minair.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepository cityRepository;

    @Transactional
    public void save(City city) {
        cityRepository.save(city);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Boolean isExistedCity(City city) {
        return cityRepository.existsByAirportCode(city.getAirportCode());
    }
}
