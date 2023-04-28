package com.minair.controller;

import com.minair.common.response.BaseResponse;
import com.minair.dto.FlightInfo;
import com.minair.dto.FlightResponseDto;
import com.minair.service.FlightClient;
import com.minair.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FlightController {

    private final FlightClient flightClient;
    private final FlightService flightService;

    @GetMapping("/flights")
    public BaseResponse<List<FlightResponseDto>> showFlights(@RequestParam(value = "flyFrom") String flyFrom,
                                                             @RequestParam(value = "flyTo") String flyTo,
                                                             @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                                             @RequestParam(value = "endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
                                                             @RequestParam(value = "day") int day,
                                                             @RequestParam(value = "people") int people) {

        FlightInfo flightInfo = flightClient.getFlightInfo(flyFrom, flyTo, startDate, endDate, day, people);
        List<FlightResponseDto> responseDtos = flightService.getDetailsFlight(flightInfo, flyTo, day);
        return new BaseResponse<>(responseDtos);
    }
}