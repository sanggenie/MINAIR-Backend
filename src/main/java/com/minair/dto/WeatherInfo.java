package com.minair.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@ToString
public class WeatherInfo {

    private ConcurrentHashMap<String, Object> daily;
}

