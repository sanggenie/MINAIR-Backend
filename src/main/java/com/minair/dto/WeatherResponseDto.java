package com.minair.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherResponseDto {

    private double averageTemperature;
    private String lastestWeather;
}
