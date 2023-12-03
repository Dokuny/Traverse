package com.traverse.command.domain.dashboard.dto;


import lombok.Builder;

@Builder
public record NowWeather(

	String district,
	String temparature,
	String weather

) {
}
