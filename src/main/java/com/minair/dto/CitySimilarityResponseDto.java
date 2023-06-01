package com.minair.dto;

import com.minair.domain.CitySimilarity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CitySimilarityResponseDto {

    private Long citySimilarityId;
    private int weight;

    public static CitySimilarityResponseDto of(CitySimilarity citySimilarity) {
        return CitySimilarityResponseDto.builder()
                .citySimilarityId(citySimilarity.getId())
                .weight(citySimilarity.getWeight())
                .build();
    }
}
