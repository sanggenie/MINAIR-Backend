package com.minair.service;

import com.minair.domain.City;
import com.minair.domain.CitySimilarity;
import com.minair.repository.CityRepository;
import com.minair.repository.CitySimilarityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public List<CitySimilarity> findByCityId(Long cityid) {
        return citySimilarityRepository.findByCityId(cityid);
    }

    public List<CitySimilarity> calculateWeights(Long cityid) {
        List<CitySimilarity> cities = findByCityId(cityid);
        
        List<Double> calculatedWeights = new ArrayList<>();
        List<CitySimilarity> result = new ArrayList<>();
        Random random = new Random();

        int count = 5;

        double totalWeight = 0.0;


        for (CitySimilarity cs : cities) {
            double weight = cs.getWeight();
            calculatedWeights.add(weight);
            totalWeight += weight;
        }

        for (int i = 0; i < count; i++) {
            double randomValue = random.nextDouble();
            double cumulativeWeight = 0.0;

            normalizeWeights(calculatedWeights, totalWeight);

            for (int j = 0; j < calculatedWeights.size(); j++) {
                cumulativeWeight += calculatedWeights.get(j);
                if (randomValue < cumulativeWeight) {
                    result.add(cities.get(j));
                    calculatedWeights.remove(j);
                    break;
                }
            }
        }

        return result;
    }
    public List<Double> normalizeWeights(List<Double> calculatedWeights, double totalWeight){
        for (int i = 0; i < calculatedWeights.size(); i++) {
            double normalizedWeight = calculatedWeights.get(i) / totalWeight;
            calculatedWeights.set(i, normalizedWeight);
        }
        return calculatedWeights;
    }
}