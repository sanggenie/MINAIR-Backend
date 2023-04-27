package com.minair.service;

import com.minair.domain.City;
import com.minair.domain.Weather;
import com.minair.dto.WeatherInfo;
import com.minair.repository.CityRepository;
import com.minair.repository.WeatherQueryRepository;
import com.minair.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherQueryRepository weatherQueryRepository;

    public void showWeatherDetails(Long cityId, LocalDate startDate, LocalDate endDate) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 city입니다."));

        List<Weather> weathers = weatherQueryRepository.findAllWeatherBetween(city.getId(), startDate, endDate);
        long dayDiff = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double averageTemperature = calculateAverageTemperature(weathers);
    }

    private double calculateAverageTemperature(List<Weather> weathers) {
        return Math.round(weathers.stream()
                .mapToDouble(Weather::getTemperature)
                .average().orElse(0.0) * 100.0) / 100.0;
    }

    @Transactional
    public void saveAllLastWeathers(WeatherInfo weatherInfo, City city) {
        List<Weather> weathers = transformInfoToEntity(weatherInfo, city);
        weatherRepository.saveAll(weathers);
    }

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
