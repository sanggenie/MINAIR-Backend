package com.minair.service;

import com.minair.domain.City;
import com.minair.domain.Weather;
import com.minair.dto.WeatherInfo;
import com.minair.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private List<Weather> transformInfoToEntity(WeatherInfo weatherInfo, City city) {
        ConcurrentHashMap<String, Object> daily = weatherInfo.getDaily();
        List<String> dailyTimes = (List<String>) daily.get("time");
        List<Double> dailyTemperature = (List<Double>) daily.get("temperature_2m_mean");
        List<LocalDate> times = dailyTimes.stream()
                .map(time -> LocalDate.parse(time, DateTimeFormatter.ISO_DATE))
                .collect(Collectors.toList());

        List<Integer> dailyWeatherCodes = (List<Integer>) daily.get("weathercode");
        List<Weather> weathers = addWeatherEntities(city, dailyTimes, dailyTemperature, times, dailyWeatherCodes);
        return weathers;
    }

    private List<Weather> addWeatherEntities(City city, List<String> dailyTimes, List<Double> dailyTemperature, List<LocalDate> times, List<Integer> dailyWeatherCodes) {
        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < dailyTimes.size(); i++) {
            weathers.add(Weather.builder()
                    .date(times.get(i))
                    .temperature(dailyTemperature.get(i))
                    .weatherCode(dailyWeatherCodes.get(i))
                    .city(city)
                    .build());
        }
        return weathers;
    }
}
