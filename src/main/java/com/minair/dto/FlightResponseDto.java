package com.minair.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FlightResponseDto {

    private String cityName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private WeatherResponseDto weather;
}
