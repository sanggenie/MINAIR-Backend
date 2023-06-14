package com.minair.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlightInfo {

    @JsonProperty("data")
    List<FlightInfoDetail> flightInfoDetails;
}
