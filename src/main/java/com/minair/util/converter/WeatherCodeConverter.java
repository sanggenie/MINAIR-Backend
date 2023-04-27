package com.minair.util.converter;

import com.minair.common.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

import static com.minair.common.exception.CustomExceptionStatus.INVALID_WEATHER_CODE;

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
        throw new GlobalException(INVALID_WEATHER_CODE);
    }
}
