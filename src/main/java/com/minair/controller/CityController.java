package com.minair.controller;

import com.minair.common.response.BaseResponse;
import com.minair.dto.CityResponseDto;
import com.minair.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CityController {

    private final CityService cityService;
    @GetMapping("/cities")
    public BaseResponse<List<CityResponseDto>> getAllCities() {
        List<CityResponseDto> responseDtos = cityService.findAllCityDetails();
        return new BaseResponse<>(responseDtos);
    }
}
