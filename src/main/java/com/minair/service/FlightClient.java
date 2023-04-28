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

    public void getFlightInfo(String flyFrom, String flyTo,
                                LocalDate startDate, LocalDate endDate, int people) {
        City from = cityRepository.findByName(flyFrom)
                .orElseThrow(() -> new GlobalException(CustomExceptionStatus.NOT_EXIST_CITY));

        City to = cityRepository.findByName(flyTo)
                .orElseThrow(() -> new GlobalException(CustomExceptionStatus.NOT_EXIST_CITY));

        URI uri = transformUri(from, to, startDate, endDate, people);
        FlightInfo flightInfo = getFlightInfo(uri);
    }

    private URI transformUri(City from, City to, LocalDate startDate, LocalDate endDate, int people) {
        return UriComponentsBuilder
                .fromUriString(flightUri)
                .queryParam("fly_from", from.getAirportCode())
                .queryParam("fly_to", to.getAirportCode())
                .queryParam("dateFrom",startDate)
                .queryParam("dateTo", endDate)
                .queryParam("adults", people)
                .queryParam("curr", "KRW")
                .build()
                .toUri();
    }

    private FlightInfo getFlightInfo(URI uri) {
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
