package com.traverse.global.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeatherErrorCode {
	BAD_REQUEST("잘못된 요청입니다.");

	private final String message;


}
