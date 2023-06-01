package com.minair.service;

import com.minair.domain.City;
import com.minair.dto.CityResponseDto;
import com.minair.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CityService {

    @Value("${cluster.file.path}")
    private String PATH;
    private final CityRepository cityRepository;

    @Transactional
    public void save(City city) {
        cityRepository.save(city);
    }

    public List<CityResponseDto> findAllCityDetails() {
        List<CityResponseDto> responseDtos = cityRepository.findAll()
                .stream()
                .map(CityResponseDto::of)
                .collect(Collectors.toList());
        return responseDtos;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Boolean isExistedCity(City city) {
        return cityRepository.existsByAirportCode(city.getAirportCode());
    }

    public ConcurrentHashMap<String, Integer> getClusters() {
        ConcurrentHashMap<String, Integer> clusters = new ConcurrentHashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(PATH, StandardCharsets.UTF_8));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] nameClusters = line.split(",");
                clusters.put(nameClusters[0], Integer.valueOf(nameClusters[1]));
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clusters;
    }
}
