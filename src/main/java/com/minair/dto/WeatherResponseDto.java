package com.minair.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WeatherResponseDto {

    private String cityName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double averageTemperature;
    private String lastestWeather;
}
