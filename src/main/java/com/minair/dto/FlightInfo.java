package com.minair.dto;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class FlightInfo {

    List<ConcurrentHashMap<String, Optional<Object>>> data;
}
