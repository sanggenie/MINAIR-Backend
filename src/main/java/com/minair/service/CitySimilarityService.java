package com.minair.service;

import com.minair.domain.City;
import com.minair.domain.CitySimilarity;
import com.minair.repository.CityRepository;
import com.minair.repository.CitySimilarityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CitySimilarityService {

    private final CityRepository cityRepository;
    private final CitySimilarityRepository citySimilarityRepository;

    @Transactional
    public void initAllSimilarity() {
        List<City> allCity = cityRepository.findAll();
        List<City> allTargetCity = cityRepository.findAll();

        allCity.stream().forEach(city -> {
            allTargetCity.stream().filter(targetCity -> !targetCity.equals(city))
                    .forEach(targetCity -> {
                        CitySimilarity citySimilarity = CitySimilarity.builder()
                                .city(city)
                                .targetCity(targetCity)
                                .weight(targetCity.getCluster() == city.getCluster() ? 40 : 1)
                                .build();
                        citySimilarityRepository.save(citySimilarity);
                    });
        });
    }
}
