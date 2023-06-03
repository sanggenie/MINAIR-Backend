package com.minair.service;

import com.minair.common.exception.GlobalException;
import com.minair.domain.City;
import com.minair.dto.FlightResponseDto;
import com.minair.dto.FlightInfo;
import com.minair.dto.WeatherResponseDto;
import com.minair.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.minair.common.exception.CustomExceptionStatus.NOT_EXIST_CITY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final WeatherService weatherService;
    private final CityRepository cityRepository;

    public List<FlightResponseDto> getDetailsFlight(FlightInfo flightInfo, String flyTo, int day, int size) {
        City city = cityRepository.findByName(flyTo)
                .orElseThrow(() -> new GlobalException(NOT_EXIST_CITY));

        List<FlightResponseDto> flightDtos = new ArrayList<>();

        ArrayList<Map.Entry<LocalDate, Float>> flights = calculateCheapestFlights(flightInfo, size);

        for (Map.Entry<LocalDate, Float> flight : flights) {
            LocalDate startDate = flight.getKey();
            LocalDate endDate = flight.getKey().plusDays(day - 1);
            WeatherResponseDto weatherResponseDto = weatherService.showWeatherDetails(city.getId(), startDate, endDate);

            flightDtos.add(
                    FlightResponseDto.builder()
                            .countryName(city.getCountry())
                            .cityName(city.getName())
                            .startDate(startDate)
                            .endDate(endDate)
                            .price(flight.getValue())
                            .weather(weatherResponseDto)
                            .build());
        }
        return flightDtos;
    }

    public ArrayList<Map.Entry<LocalDate, Float>> calculateCheapestFlights(FlightInfo flightInfo, int size) {
        ConcurrentHashMap<LocalDate, Float> flights = new ConcurrentHashMap<>();

        List<ConcurrentHashMap<String, Optional<Object>>> datas = flightInfo.getData();
        int idx = 0;
        for (ConcurrentHashMap<String, Optional<Object>> data : datas) {
            idx += 1;
            String localArrival = (String) data.get("local_arrival").orElse(null);
            String subString = localArrival.substring(0, 10);
            LocalDate date = LocalDate.parse(subString);
            float price = Float.parseFloat(String.valueOf(data.get("price").orElse(0.0)));
            flights.putIfAbsent(date, price);
            if (flights.size() == size)  break;
        }

        ArrayList<Map.Entry<LocalDate, Float>> entries = new ArrayList<>(flights.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<LocalDate, Float>>() {
            @Override
            public int compare(Map.Entry<LocalDate, Float> o1, Map.Entry<LocalDate, Float> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        return entries;
    }
}
