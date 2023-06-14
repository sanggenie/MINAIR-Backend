package com.minair.dto;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class WeatherInfo {

    private ConcurrentHashMap<String, Object> daily;
}

