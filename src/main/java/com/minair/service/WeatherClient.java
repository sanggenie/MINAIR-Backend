package com.minair.service;

import com.minair.domain.City;
import com.minair.dto.WeatherInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WeatherClient {

    private final WebClient webClient;

    private static final String WEATHER_URI = "https://archive-api.open-meteo.com/v1/archive";

    public WeatherInfo getWeatherInfo(City city, LocalDate startDate, LocalDate endDate) {
        URI uri = transformUri(city, startDate, endDate);
        return getWeatherInfo(uri);
    }

    private URI transformUri(City city, LocalDate startDate, LocalDate endDate) {
        return UriComponentsBuilder
                .fromUriString(WEATHER_URI)
                .queryParam("daily","temperature_2m_mean,weathercode")
                .queryParam("timezone", "GMT")
                .queryParam("longitude",city.getLongitude())
                .queryParam("latitude", city.getLatitude())
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .build()
                .toUri();
    }

    private WeatherInfo getWeatherInfo(URI uri) {
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WeatherInfo.class)
                .block();
    }
}
