package com.minair.controller;

import com.minair.domain.City;
import com.minair.domain.Location;
import com.minair.dto.WeatherInfo;
import com.minair.service.CityService;
import com.minair.service.CitySimilarityService;
import com.minair.service.WeatherClient;
import com.minair.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class InitDb {

    private final CitySimilarityService citySimilarityService;
    private final CityService cityService;
    private final WeatherClient weatherClient;
    private final WeatherService weatherService;

    @PostMapping("/init-city")
    public void initCity() {
        ConcurrentHashMap<String, Integer> clusters = cityService.getClusters();

        Arrays.stream(Location.values())
                .forEach(location -> {
                    location.initCluster(clusters.get(location.getCityName()));
                });

        Arrays.stream(Location.values())
                .map(City::of)
                .filter(city -> !cityService.isExistedCity(city))
                .forEach(cityService::save);
    }

    @PostMapping("/init-weather")
    public void initWeather() {
        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2022, 12, 31);
        cityService.findAll()
                .forEach(city -> {
                    WeatherInfo weatherInfo = weatherClient.getWeatherInfo(city, from, end);
                    weatherService.saveAllLastWeathers(weatherInfo, city);
                });
    }

    @PostMapping("/init-similarity")
    public void initSimilarity() {
        citySimilarityService.initAllSimilarity();
    }
}
