package com.minair.dto;

import com.minair.domain.City;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CityResponseDto {

    private Long cityId;
    private String country;
    private String name;
    private String airportCode;

    public static CityResponseDto of(City city) {
        return CityResponseDto.builder()
                .cityId(city.getId())
                .country(city.getCountry())
                .name(city.getName())
                .airportCode(city.getAirportCode())
                .build();
    }
}
