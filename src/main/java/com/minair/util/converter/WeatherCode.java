package com.minair.util.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeatherCode {

    SUNNY("맑음", 1),
    CLOUDY("흐림", 3),
    RAINY("비", 50);

    private final String weatherCondition;
    private final int code;
}
