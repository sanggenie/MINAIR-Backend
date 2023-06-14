package com.minair.service;

import com.minair.common.exception.GlobalException;
import com.minair.domain.City;
import com.minair.domain.CitySimilarity;
import com.minair.dto.CitySimilarityResponseDto;
import com.minair.repository.CityRepository;
import com.minair.repository.CitySimilarityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.minair.common.exception.CustomExceptionStatus.NOT_EXIST_CITY;
import static com.minair.common.exception.CustomExceptionStatus.SERVER_ERROR;

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

    public List<City> getSimilarCities(String cityName) {
        List<CitySimilarity> citySimilarities = citySimilarityRepository.findAllByCityName(cityName);

        Set<CitySimilarity> result = calculateWeightedRandom(citySimilarities);

        List<City> similarCities = result.stream()
                .map(CitySimilarity::getTargetCity)
                .collect(Collectors.toList());

        return similarCities;
    }


    @Transactional
    public CitySimilarityResponseDto updateWeight(Long cityId, Long targetCityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new GlobalException(NOT_EXIST_CITY));

        City targetCity = cityRepository.findById(targetCityId)
                .orElseThrow(() -> new GlobalException(NOT_EXIST_CITY));

        CitySimilarity citySimilarity = citySimilarityRepository.findByCityIdAndTargetCityId(cityId, targetCityId)
                .orElseThrow(() -> new GlobalException(SERVER_ERROR));

        citySimilarity.update();

        return CitySimilarityResponseDto.of(citySimilarity);
    }

    private Set<CitySimilarity> calculateWeightedRandom(List<CitySimilarity> citySimilarities) {
        List<Double> calculatedWeights = new ArrayList<>();
        Set<CitySimilarity> result = new HashSet<>();
        Random random = new Random();

        int count = 2;

        for (CitySimilarity cs : citySimilarities) {
            double weight = cs.getWeight();
            calculatedWeights.add(weight);
        }

        for (int i = 0; i < count; i++) {
            double randomValue = random.nextDouble();
            double cumulativeWeight = 0.0;

            normalizeWeights(calculatedWeights);

            for (int j = 0; j < calculatedWeights.size(); j++) {
                cumulativeWeight += calculatedWeights.get(j);
                if (randomValue < cumulativeWeight) {
                    result.add(citySimilarities.get(j));
                    citySimilarities.remove(j);
                    calculatedWeights.remove(j);
                    break;
                }
            }
        }
        return result;
    }

    private void normalizeWeights(List<Double> calculatedWeights){
        double totalWeight = 0;
        for(Double cw: calculatedWeights)
            totalWeight += cw;

        for (int i = 0; i < calculatedWeights.size(); i++) {
            double normalizedWeight = calculatedWeights.get(i) / totalWeight;
            calculatedWeights.set(i, normalizedWeight);
        }
    }
}

