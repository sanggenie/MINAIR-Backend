package com.minair.util.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public class WeatherCodeConverter {

    public static int convertWeatherCode(Integer code) {
        if (code >= 0 && code <= 2) return 1;
        if (code >= 3 && code <= 49) return 3;
        if (code >= 50 && code <= 99) return 50;
        throw new NoSuchElementException("exception!");
    }

    public static String getWeatherCondition(int code) {
        for (WeatherCode weatherCode : WeatherCode.values()) {
            if (weatherCode.getCode() == code) {
                return weatherCode.getWeatherCondition();
            }
        }
        throw new IllegalArgumentException("존재하지 않는 날씨입니다.");
    }
}
