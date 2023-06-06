package com.minair.service;

import com.minair.common.exception.GlobalException;
import com.minair.domain.City;
import com.minair.dto.FlightDetailsReponseDto;
import com.minair.dto.FlightInfo;
import com.minair.dto.FlightInfoDetail;
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

        String airportCode = flightInfo.getFlightInfoDetails().stream()
                .map(FlightInfoDetail::getFlyTo)
                .findFirst().orElseThrow(() -> new GlobalException(NOT_EXIST_CITY));

        City city = cityRepository.findByAirportCode(airportCode)
                .orElseThrow(() -> new GlobalException(NOT_EXIST_CITY));

        List<FlightDetailsReponseDto> flightDtos = new ArrayList<>();
        ConcurrentHashMap<String, String> links = new ConcurrentHashMap<>();

        ArrayList<Map.Entry<LocalDate, Float>> flights = calculateCheapestFlights(flightInfo, size, links);

        for (Map.Entry<LocalDate, Float> flight : flights) {
            LocalDate startDate = flight.getKey();
            LocalDate endDate = flight.getKey().plusDays(day - 1);
            Float price = flight.getValue();
            WeatherResponseDto weatherResponseDto = weatherService.showWeatherDetails(city.getId(), startDate, endDate);
            String link = links.get(startDate + ":" + String.valueOf(price));
            link = link.replace("lang=en", "lang=ko");
            flightDtos.add(
                    FlightDetailsReponseDto.builder()
                            .startDate(startDate)
                            .endDate(endDate)
                            .price(price)
                            .link(link)
                            .weather(weatherResponseDto)
                            .build());
        }
        return FlightResponseDto.of(city, flightDtos);
    }

    private ArrayList<Map.Entry<LocalDate, Float>> calculateCheapestFlights(FlightInfo flightInfo, int size, ConcurrentHashMap<String, String> links) {
        ConcurrentHashMap<LocalDate, Float> flights = new ConcurrentHashMap<>();

        List<FlightInfoDetail> flightInfoDetails = flightInfo.getFlightInfoDetails();
        for (FlightInfoDetail flightInfoDetail : flightInfoDetails) {
            String localArrival = flightInfoDetail.getLocalArrival().substring(0, 10);
            LocalDate date = LocalDate.parse(localArrival);
            float price = flightInfoDetail.getPrice();

            links.put(date+":"+String.valueOf(price), flightInfoDetail.getDeepLink());
            flights.putIfAbsent(date, price);
            if(flights.size() == size) break;
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
