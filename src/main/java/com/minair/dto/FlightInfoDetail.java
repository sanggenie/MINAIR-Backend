package com.minair.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FlightInfoDetail {

    String flyTo;

    @JsonProperty("local_arrival")
    String localArrival;

    @JsonProperty("price")
    float price;

    @JsonProperty("deep_link")
    String deepLink;
}
