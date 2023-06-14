package com.minair.controller;

import com.minair.common.response.BaseResponse;
import com.minair.dto.CitySimilarityResponseDto;
import com.minair.service.CitySimilarityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CitySimilarityController {

    private final CitySimilarityService citySimilarityService;

    @PatchMapping("/cities/{cityId}/target-cities/{targetCityId}")
    public BaseResponse<CitySimilarityResponseDto> clickSimilarity(@PathVariable("cityId") Long cityId,
                                                                   @PathVariable("targetCityId") Long targetCityId) {
        CitySimilarityResponseDto responseDto = citySimilarityService.updateWeight(cityId, targetCityId);
        return new BaseResponse<>(responseDto);
    }
}
