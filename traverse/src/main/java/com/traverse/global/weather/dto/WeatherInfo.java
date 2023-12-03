package com.traverse.global.weather.dto;

import lombok.Builder;

@Builder
public record WeatherInfo(
	String weathewr,
	String temparature
) {

}
