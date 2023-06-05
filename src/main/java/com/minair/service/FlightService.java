package com.minair.service;

import com.minair.common.exception.GlobalException;
import com.minair.domain.City;
import com.minair.dto.FlightDetailsReponseDto;
import com.minair.dto.FlightInfo;
import com.minair.dto.FlightResponseDto;
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
import java.util.stream.Collectors;

import static com.minair.common.exception.CustomExceptionStatus.NOT_EXIST_CITY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final WeatherService weatherService;
    private final CityRepository cityRepository;

    public List<FlightResponseDto> getDetailsFlights(List<FlightInfo> flightInfos, int day, int size) {
        List<FlightResponseDto> responseDtos = flightInfos.stream()
                .map(flightInfo -> getDetailsFlight(flightInfo, day, size))
                .collect(Collectors.toList());

        return responseDtos;
    }

    public FlightResponseDto getDetailsFlight(FlightInfo flightInfo, int day, int size) {

        String airportCode = (String) flightInfo.getData().stream()
                .map(data -> data.get("flyTo"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("error"))
                .get();

        City city = cityRepository.findByAirportCode(airportCode)
                .orElseThrow(() -> new GlobalException(NOT_EXIST_CITY));

        List<FlightDetailsReponseDto> flightDtos = new ArrayList<>();
        ArrayList<Map.Entry<LocalDate, Float>> flights = calculateCheapestFlights(flightInfo, size);

        for (Map.Entry<LocalDate, Float> flight : flights) {
            LocalDate startDate = flight.getKey();
            LocalDate endDate = flight.getKey().plusDays(day - 1);
            WeatherResponseDto weatherResponseDto = weatherService.showWeatherDetails(city.getId(), startDate, endDate);

            flightDtos.add(
                    FlightDetailsReponseDto.builder()
                            .startDate(startDate)
                            .endDate(endDate)
                            .price(flight.getValue())
                            .weather(weatherResponseDto)
                            .build());
        }
        return FlightResponseDto.of(city, flightDtos);
    }

    public ArrayList<Map.Entry<LocalDate, Float>> calculateCheapestFlights(FlightInfo flightInfo, int size) {
        ConcurrentHashMap<LocalDate, Float> flights = new ConcurrentHashMap<>();

        List<ConcurrentHashMap<String, Optional<Object>>> datas = flightInfo.getData();

        for (ConcurrentHashMap<String, Optional<Object>> data : datas) {
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
