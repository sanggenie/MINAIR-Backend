package com.minair.dto;

import com.minair.domain.City;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FlightResponseDto {

    private Long cityId;
    private String countryName;
    private String cityName;
    List<FlightDetailsReponseDto> flights;

    public static FlightResponseDto of(City city, List<FlightDetailsReponseDto> flights) {
        return FlightResponseDto.builder()
                .cityId(city.getId())
                .countryName(city.getCountry())
                .cityName(city.getName())
                .flights(flights)
                .build();
    }
}
