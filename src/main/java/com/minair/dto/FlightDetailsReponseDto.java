package com.minair.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FlightDetailsReponseDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private WeatherResponseDto weather;
}
