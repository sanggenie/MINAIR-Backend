package com.minair.service;

import com.minair.common.exception.CustomExceptionStatus;
import com.minair.common.exception.GlobalException;
import com.minair.domain.City;
import com.minair.dto.FlightInfo;
import com.minair.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FlightClient {

    private final CityRepository cityRepository;
    private final WebClient webClient;

    @Value("${api.client.flight.uri}")
    private String flightUri;

    @Value("${api.client.flight.key}")
    private String apikey;

    public FlightInfo getFlightInfo(String flyFrom, String flyTo,
                                LocalDate startDate, LocalDate endDate, int day,int people) {
        City from = cityRepository.findByName(flyFrom)
                .orElseThrow(() -> new GlobalException(CustomExceptionStatus.NOT_EXIST_CITY));

        City to = cityRepository.findByName(flyTo)
                .orElseThrow(() -> new GlobalException(CustomExceptionStatus.NOT_EXIST_CITY));

        URI uri = transformUri(from, to, startDate, endDate, day, people);
        return requestFlightInfo(uri);
    }

    private URI transformUri(City from, City to, LocalDate startDate, LocalDate endDate, int day, int people) {
        return UriComponentsBuilder
                .fromUriString(flightUri)
                .queryParam("fly_from", from.getAirportCode())
                .queryParam("fly_to", to.getAirportCode())
                .queryParam("date_from",startDate)
                .queryParam("date_to", endDate)
                .queryParam("nights_in_dst_from", day-1)
                .queryParam("nights_in_dst_to", day-1)
                .queryParam("adults", people)
                .queryParam("curr", "KRW")
                .build()
                .toUri();
    }

    private FlightInfo requestFlightInfo(URI uri) {
        return webClient
                .get()
                .uri(uri)
                .headers(header ->
                        header.set("apikey", apikey))
                .retrieve()
                .bodyToMono(FlightInfo.class)
                .block();
    }
}
