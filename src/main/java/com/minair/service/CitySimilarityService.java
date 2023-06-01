package com.minair.service;

import com.minair.domain.City;
import com.minair.domain.CitySimilarity;
import com.minair.repository.CitySimilarityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CitySimilarityService {

    @Autowired
    private CitySimilarityRepository citySimilarityRepository;

    public List<CitySimilarity> findByCityId(Long cityid) {
        return citySimilarityRepository.findByCityId(cityid);
    }

    public List<CitySimilarity> calculateWeights(Long cityid) {
        List<CitySimilarity> cities = findByCityId(cityid);

        List<Double> calculatedWeights = new ArrayList<>();
        List<CitySimilarity> result = new ArrayList<>();
        Random random = new Random();

        int count = 2;

        double totalWeight = 0.0;


        for (CitySimilarity cs : cities) {
            double weight = cs.getWeight();
            calculatedWeights.add(weight);
            totalWeight += weight;
        }


        for (int i = 0; i < calculatedWeights.size(); i++) {
            double normalizedWeight = calculatedWeights.get(i) / totalWeight;
            calculatedWeights.set(i, normalizedWeight);
        }


        for (int i = 0; i < count; i++) {
            double randomValue = random.nextDouble();
            double cumulativeWeight = 0.0;

            for (int j = 0; j < calculatedWeights.size(); j++) {
                cumulativeWeight += calculatedWeights.get(j);
                if (randomValue < cumulativeWeight) {
                    result.add(cities.get(j));
                    break;
                }
            }
        }
        return result;
    }



}
